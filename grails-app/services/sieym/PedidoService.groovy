package sieym

class PedidoService {

    def evaluarAlternativas(Pedido pedido) {
		def altPorFase = [:]
		Fase.list().each { Fase fase ->
			def maquinas = Maquina.findAllByFase(fase)
			def alt = pedido.generarAlternativasProduccion(maquinas, [:])
			altPorFase.put(fase, alt)
		}
		return altPorFase
    }
}
