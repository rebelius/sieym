package sieym

import org.joda.time.DateTime;
import org.joda.time.Duration
import org.joda.time.Interval;

class PedidoService {

	def planificar(Pedido pedido) {
		def fases = Fase.listOrderById()
		def resPorFase = this.generarReservasPorFase(pedido, fases)
		def tiempoEmpaquetado = pedido.cacularTiempoEmpaquetado()
		def fechaPedidoTerminado = resPorFase[fases.last()]['intervalo'].end.plus(tiempoEmpaquetado)
		pedido.estado = EstadoPedido.Planificado
		pedido.save(flush: true)
		[fases: fases, reservas: resPorFase, tiempoEmpaquetado: tiempoEmpaquetado,
			fechaPedidoTerminado: fechaPedidoTerminado]
	}

	def generarReservasPorFase(Pedido pedido, fases) {
		def desde = null
		fases.collectEntries { Fase fase ->
			def maquinas = Maquina.findAllByFase(fase)
			def res = generarReserva(pedido, fase, desde, maquinas)
			desde = res.intervalo.end
			[fase, res]
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
