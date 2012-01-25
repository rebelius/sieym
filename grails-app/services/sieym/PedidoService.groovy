package sieym

import org.joda.time.DateTime;
import org.joda.time.Interval;

class PedidoService {

	def planificar(Pedido pedido) {
		def fases = Fase.listOrderById()
		def resPorFase = this.generarReservasPorFase(pedido, fases)
		def tiempoEmpaquetado = pedido.cacularTiempoEmpaquetado()
		def fechaPedidoTerminado = resPorFase[fases.last()].intervalo.end.plus(tiempoEmpaquetado)
		[fases: fases, reservas: resPorFase, tiempoEmpaquetado: tiempoEmpaquetado,
			fechaPedidoTerminado: fechaPedidoTerminado]
	}

	def generarReservasPorFase(Pedido pedido, fases) {
		def resPorFase = [:]
		def comienzoDeFase = new DateTime()
		fases.each { Fase fase ->
			def reservas = []
			def maquinas = Maquina.findAllByFase(fase)
			def intervaloLibre = maquinas.first().verificarDisponibilidad(comienzoDeFase).first()
			maquinas.each {
				def res = new ReservaMaquina(pedido: pedido, intervalo: new Interval(comienzoDeFase,  fase.duracion))
				it.addToReservas(res)//.save(flush: true)
				reservas += res
			}
			resPorFase.put(fase, reservas)
			comienzoDeFase = reservas.first().intervalo.end
		}
		return resPorFase
	}

}
