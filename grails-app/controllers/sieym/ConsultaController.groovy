package sieym

import org.springframework.dao.DataIntegrityViolationException

class ConsultaController {


	PedidoService pedidoService

	def index() {
		redirect(action: "list", params: params)
	}

	

	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[pedidoInstanceList: Pedido.findAllByEstado(EstadoPedido.Produccion, params), pedidoInstanceTotal: Pedido.countByEstado(EstadoPedido.Produccion),faseInstanceList: Fase.list(), faseInstanceTotal: Fase.count()]
		
	}

}
