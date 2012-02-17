package sieym


class ConsultaController {


	PedidoService pedidoService

	def index() {
		redirect(action: "list", params: params)
	}

	

	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		if(session.user.role==sieym.Role.USER){
		[pedidoInstanceList: Pedido.findAllByEstadoAndCliente(EstadoPedido.Produccion,session.user, params),
			ped: Pedido.findAllByEstadoAndCliente(EstadoPedido.Embolsado,session.user, params), 
			pedTo: Pedido.countByEstadoAndCliente(EstadoPedido.Embolsado,session.user),
			 pedidoInstanceTotal: Pedido.countByEstadoAndCliente(EstadoPedido.Produccion,session.user),
			 faseInstanceList: Fase.list(), faseInstanceTotal: Fase.count()]
		}else{
			[pedidoInstanceList: Pedido.findAllByEstado(EstadoPedido.Produccion, params),
			ped: Pedido.findAllByEstado(EstadoPedido.Embolsado, params),
			pedTo: Pedido.countByEstado(EstadoPedido.Embolsado),
			 pedidoInstanceTotal: Pedido.countByEstado(EstadoPedido.Produccion),
			 faseInstanceList: Fase.list(), faseInstanceTotal: Fase.count()]
		
		}
		
	}
	def list1() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		if(session.user.role==sieym.Role.USER){
		[pedidoInstanceList: Pedido.findAllByEstadoAndCliente(EstadoPedido.EnViaje,session.user, params),
			 pedidoInstanceTotal: Pedido.countByEstadoAndCliente(EstadoPedido.EnViaje,session.user)]
		}else{	
		[pedidoInstanceList: Pedido.findAllByEstado(EstadoPedido.EnViaje, params),
			 pedidoInstanceTotal: Pedido.countByEstado(EstadoPedido.EnViaje)]
		}
		
	}
}
