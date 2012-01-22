import sieym.MateriaPrima;

import java.util.List;

import org.joda.time.Duration

import sieym.ComponenteProducto;
import sieym.EstadoPedido;
import sieym.Fase
import sieym.ItemPedido;
import sieym.Logistica;
import sieym.Maquina
import sieym.MateriaPrima;
import sieym.Pedido;
import sieym.Producto;
import sieym.Role;
import sieym.Paquete;

import sieym.User


class BootStrap {
	
	def fases

	def init = { servletContext ->
		User user = new User(
				name: "Admin User",
				dni: 66666666,
				username: "admin",
				email: "admin@sieym.com",
				phone: "4555-5555",
				address: "Street 1000",
				password: "shhhh",
				active: true,
				role: Role.ADMIN
				)
		user.save()
		println user.errors
		user = new User(
				name: "Regular User",
				dni: 555555555,
				username: "user",
				email: "user@sieym.com",
				phone: "4555-4444",
				address: "Street 2000",
				password: "hola",
				active: true,
				role: Role.USER
				)
		user.save()
		new Logistica(precioPorKm: 250, tiempoPorKm: 1.5).save()
		
		Fase sapecado = new Fase(nombre: "Sapecado", duracion: Duration.standardHours(9))
		Fase canchado = new Fase(nombre: "Canchado", duracion: Duration.standardHours(10))
		Fase molienda = new Fase(nombre: "Molienda", duracion: Duration.standardHours(10))
		def fases = [sapecado, canchado, molienda]
		fases*.save()
		this.fases = fases
		
		Maquina mq1 = new Maquina(descripcion: "MQ_SAP_60", fase: sapecado, capacidad: 60, rendimiento: 0.6)
		Maquina mq2 = new Maquina(descripcion: "MQ_SAP_120", fase: sapecado, capacidad: 120, rendimiento: 0.72)
		Maquina mq3 = new Maquina(descripcion: "MQ_SAP_50", fase: sapecado, capacidad: 50, rendimiento: 0.82)
		
		Maquina mq4 = new Maquina(descripcion: "MQ_CAN_100", fase: canchado, capacidad: 100, rendimiento: 0.6)
		Maquina mq5 = new Maquina(descripcion: "MQ_CAN_120", fase: canchado, capacidad: 120, rendimiento: 0.72)
		
		Maquina mq6 = new Maquina(descripcion: "MQ_MOL_150", fase: molienda, capacidad: 150, rendimiento: 0.82)
		
		def maquinas = [mq1, mq2, mq3, mq4, mq5, mq6]
		maquinas*.save()
		
		MateriaPrima mp1 = this.createMateriaPrima("MP1", ['12', '25', '32'])
		MateriaPrima mp2 = this.createMateriaPrima("MP2", ['13', '26', '9'])
		MateriaPrima mp3 = this.createMateriaPrima("MP3", ['15', '21', '25'])
		MateriaPrima mp4 = this.createMateriaPrima("MP4", ['11', '28', '19'])
		[mp1, mp2, mp3, mp4]*.save(flush: true)
		println mp1.errors
		
		Producto pA = new Producto(nombre: "Producto A", composicion:
				[
					new ComponenteProducto(porcentaje: 25, materiaPrima: mp1),
					new ComponenteProducto(porcentaje: 20, materiaPrima: mp2),
					new ComponenteProducto(porcentaje: 55, materiaPrima: mp3)
				])

		Producto pB = new Producto(nombre: "Producto B", composicion:
				[
					new ComponenteProducto(porcentaje: 52, materiaPrima: mp2),
					new ComponenteProducto(porcentaje: 48, materiaPrima: mp4)
				])
		def productos = [pA, pB]
		productos*.save()
		
		MateriaPrima sal = new MateriaPrima(nombre: 'Sal', descripcion: 'Para salar').save()
		MateriaPrima pimienta = new MateriaPrima(nombre: 'Pimienta', descripcion: 'Para pimentar').save()
		Producto p = new Producto(nombre: 'Sal y Pimienta')
		p.addToComposicion(new ComponenteProducto(materiaPrima: sal, porcentaje: 60))
		p.addToComposicion(new ComponenteProducto(materiaPrima: pimienta, porcentaje: 40))
		p.save()
		
		Paquete paq = new Paquete(name: "A", descripcion: "Paquete tipo A", capacidad: 23, tiempoArmado: 30)
		paq.save()
		
		Paquete paq2 = new Paquete(name: "B", descripcion: "Paquete tipo B", capacidad: 10, tiempoArmado: 20)
		paq2.save()
		
		EstadoPedido.values()[0..3].each { def ep ->
			2.times { def time ->
				def items = [new ItemPedido(producto: p, paquete: paq, cantidad: 5 * (1 + time) * (1 + ep.ordinal()))]
				Pedido ped = new Pedido(cliente: user, items: items, estado: ep, fechaPedido: new Date(), direccionEntrega: "Pichincha 1234")
				ped.save()
			}
		}
	}
	
	def destroy = {
	}
	
	private MateriaPrima createMateriaPrima(String nombre, List cpValues) {
		Map cp = [(this.fases[0].nombre): cpValues[0],
					(this.fases[1].nombre): cpValues[1],
					(this.fases[2].nombre): cpValues[2]]
		MateriaPrima mp = new MateriaPrima(nombre: nombre)
		mp.coeficienteProduccion = cp
		return mp
	}

}
