package sieym

import java.util.Date;

import org.joda.time.DateTime;

class SobranteService {

    int consultarSobrantesDisponibles(Producto producto) {
		def c = Sobrante.createCriteria()
		def cantidadDisponibe = c.get {
			projections {
				sum('cantidad')
			}
			and {
				eq('producto.id', producto.id)
				le('disponible', new Date())
			}
		}
		cantidadDisponibe ?: 0
    }
	
	void usarSobrantesDisponibles(Producto producto, int cantidad) {
		def now = new Date()
		def disp = Sobrante.findAllByProductoAndDisponibleLessThanEquals(producto, now)
		int cant = 0
		def usados = []
		for(sob in disp) {
			cant += sob.cantidad
			usados += sob
			if(cant >= cantidad) break
		}
		usados*.delete(flush: true)
		def diff = cant - cantidad
		if(diff > 0) {
			new Sobrante(producto: producto, cantidad: diff, disponible: now).save(flush: true)
		}
	}
	
	void registrarSobrantes(Pedido pedido, int capacidadMaquinas, Date disponible) {
		def pesoTotal = pedido.calcularPesoTotal()
		def sobranteTotal = capacidadMaquinas - pesoTotal
		pedido.items.each {
			Producto prod = it.producto
			int pesoItem = it.paquete.capacidad * it.cantidad
			int sob = sobranteTotal * (pesoItem / pesoTotal)
			new Sobrante(producto: prod, cantidad: sob, disponible: disponible).save(flush: true)
		}
	}

}
