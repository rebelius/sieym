package sieym


class ConsultaController {


	PedidoService pedidoService

	def index() {
		redirect(action: "list", params: params)
	}

	

	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		[pedidoInstanceList: Pedido.findAllByEstado(EstadoPedido.Produccion, params),
			ped: Pedido.findAllByEstado(EstadoPedido.Embolsado, params), 
			pedTo: Pedido.countByEstado(EstadoPedido.Embolsado),
			 pedidoInstanceTotal: Pedido.countByEstado(EstadoPedido.Produccion),
			 faseInstanceList: Fase.list(), faseInstanceTotal: Fase.count()]
		
	}
	def list1() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		[pedidoInstanceList: Pedido.findAllByEstado(EstadoPedido.EnViaje, params),
			 pedidoInstanceTotal: Pedido.countByEstado(EstadoPedido.EnViaje)]
		
	}
}
