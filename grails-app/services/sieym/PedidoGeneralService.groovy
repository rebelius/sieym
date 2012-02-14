package sieym

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration
import org.joda.time.Interval;

class PedidoGeneralService {

	def planificar(Pedido pedido) {
//		pedido.estado=EstadoPedido.Planificado
		
	
		
		def sobrantesDisponibles=Sobrante.list()
		println "check sobrante"
		Map<Producto,Integer>paquetesTotales= pedido.getTotalToneladas()
		//se pregunta por cada sobrante si existe algun producto disponible 
		sobrantesDisponibles.each {
				//se pasa a no disponible se le asgna el pedido y paso a setear el sobrante 
				Integer sobra=it.cantidad
				
				if (sobra>paquetesTotales.get(it.producto)){
					new SobrantePedido(producto:it.producto,cantidad:paquetesTotales.get(it.producto),pedido:pedido).save(flush:true)
					new RealPedido(producto:it.producto,cantidad:0,pedido:pedido).save(flush:true)
					it.cantidad-=paquetesTotales.get(it.producto)
					
				}else {
					new SobrantePedido(producto:it.producto,cantidad:sobra,pedido:pedido).save(flush:true)
					new RealPedido(producto:it.producto,cantidad:paquetesTotales.get(it.producto)-sobra,pedido:pedido).save(flush:true)
					it.cantidad=0
				}
				it.save(flush:true)
				
				paquetesTotales.remove(it.producto)
				println "Hay sobrantes " + it.producto
		}
		paquetesTotales.each() { key, value ->
			println "acass "+key+" "+value
			def tf=RealPedido.findByPedidoAndProducto(pedido,key)?:new RealPedido(producto:key,cantidad:value,pedido:pedido).save(flush:true)
			println "acass"
		}
		
		int ton= RealPedido.findAllByPedido(pedido).sum({it.cantidad	})
		ton+=SobrantePedido.findAllByPedido(pedido).sum({it.cantidad	})?:0
		println " Calcular Coeficiente de produccion por pedidos"
		def desde = new DateTime()
		Fase.list().each {
			println " ------------   " +it.nombre+" -------------------"
			float coef=pedido.calcularCoeficienteProduccion(it)
			println "coeficiente produccion ${coef}"
			def maq= Maquina.findAllByCapacidadGreaterThanEqualsAndFase(ton,it)?: Maquina.findAllByCapacidadLessThanEqualsAndFase(ton,it)
			println maq
			//verifica si la maquina lo puede contener sino trae todas las maquias
			if( maq instanceof Maquina){
				
			}else{
//			float tot=maq.rendimiento*(it.duracion.getStandardHours()/60)*coef
//			println tot
			def res = generarReserva(pedido, it, desde, maq)
			desde = res.intervalo.end
			
			}
			
			println " -------------"
		}
		
	}
	def generarReserva(Pedido pedido, Fase fase, DateTime desde, List<Maquina> maquinas) {
		Float pesoTotal = pedido.calcularPesoTotal([:])
		Float capacidadTotal = maquinas.sum {it.capacidad}
		if(pesoTotal > capacidadTotal) {
			throw new IllegalStateException("El pedido excede la capacidad total para la Fase ${fase.nombre}. ${pesoTotal} > ${capacidadTotal}")
		}
		// Primero busco la maquina de capacidad optima para el pedido
		Maquina maquina = maquinas.findAll({it.capacidad >= pesoTotal}).min({it.capacidad})
		if(maquina){
			def duracion = pedido.calcularDuracion(maquina)
			def intervaloProximo = maquina.verificarDisponibilidad(desde, duracion).first()
			def intervalo = new Interval(intervaloProximo.start,  duracion)
			this.reservarMaquina(maquina, pedido, intervalo)
			return [intervalo: intervalo, maquinas: [maquina]]
		}
		// Si el pedido excede todas las maquinas, vamos barriendo de la mas grande
		// a la mas chica hasta completar la capacidad
		int capacidad = 0
		def mqs = []
		for(mq in maquinas.sort({-it.capacidad})) {
			capacidad += mq.capacidad
			mqs << mq
			if(capacidad >= pesoTotal) break
		}
		def duracion = pedido.calcularDuracion(mqs)
		def intervalo = this.buscarIntervaloEnComun(mqs, desde, duracion)
		mqs.each {reservarMaquina(it, pedido, intervalo)}
		return [intervalo: intervalo, maquinas: mqs]
	}
	
	def buscarIntervaloEnComun(def mqs, def desde, def duracion) {
		def start = mqs.collect({it.verificarDisponibilidad(desde, duracion).last().start}).max()
		return new Interval(start, duracion)
	}
	
	def reservarMaquina(def mq, def pedido, def intervalo) {
		def res = new ReservaMaquina(pedido: pedido)
		res.intervalo = intervalo
		mq.addToReservas(res).save(flush: true)
		return res
	}
	
}
