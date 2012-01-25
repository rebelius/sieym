package sieym

import org.springframework.dao.DataIntegrityViolationException

class PedidoController {


	PedidoService pedidoService

	def index() {
		redirect(action: "list", params: params)
	}

	def planificar() {
		Pedido pedido = Pedido.get(params.id)
		if(pedido.items){
			def alt = this.pedidoService.planificar(pedido)
			[pedido: pedido] + alt
		} else {
			flash.message = "No se puede planificar un Pedido sin Items"
		}
	}

	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		if(params.estado) {
			EstadoPedido estado = EstadoPedido.valueOf(params.estado)
			[pedidoInstanceList: Pedido.findAllByEstado(estado, params), pedidoInstanceTotal: Pedido.countByEstado(estado),estado:estado]
		} else {
			[pedidoInstanceList: Pedido.list(params), pedidoInstanceTotal: Pedido.count()]
		}
	}

	def create() {
		[pedidoInstance: new Pedido(params)]
	}

	def save() {
		def pedidoInstance = new Pedido(params)
		if(session.user.role==sieym.Role.USER){
			pedidoInstance.cliente=session.user
		}
		pedidoInstance.estado = EstadoPedido.Proceso
		pedidoInstance.fechaPedido = new Date()
		if (!pedidoInstance.save(flush: true)) {
			render(view: "create", model: [pedidoInstance: pedidoInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [
			message(code: 'pedido.label', default: 'Pedido'),
			pedidoInstance.id
		])
		redirect(action: "show", id: pedidoInstance.id)
	}

	def show() {
		def pedidoInstance = Pedido.get(params.id)
		if (!pedidoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'pedido.label', default: 'Pedido'),
				params.id
			])
			redirect(action: "list")
			return
		}

		[pedidoInstance: pedidoInstance]
	}

	def edit() {
		def pedidoInstance = Pedido.get(params.id)
		if (!pedidoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'pedido.label', default: 'Pedido'),
				params.id
			])
			redirect(action: "list")
			return
		}

		[pedidoInstance: pedidoInstance]
	}
	def pasarAceptado(){
		def pedidoInstance = Pedido.get(params.id)
		if (!pedidoInstance) {
			flash.error = message(code: 'default.not.found.message', args: [
				message(code: 'pedido.label', default: 'Pedido'),
				params.id
			])
			redirect(action: "list", 'params': [chooseEstado: 1])
			return
		}else{
			pedidoInstance.estado=EstadoPedido.Aceptado
			if(!pedidoInstance.save(flush:true)){
				flash.error = message(code: 'default.not.save.message', args: [
					message(code: 'pedido.label', default: 'No se pudo guardar el cambio intentelo nuevamente'),
					params.id
				])
			}else{
				flash.message = message(code: 'default.save.Aceptado', args: [
					params.id
				])
			}
			redirect(action: "list", 'params': [estado:params?.estado])
			
		}
		
		
	}
	def cambiarEstado() {
		def pedidoInstance = Pedido.get(params.id)
		if (!pedidoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'pedido.label', default: 'Pedido'),
				params.id
			])
			redirect(action: "list", 'params': [chooseEstado: 1])
			return
		}

		[pedidoInstance: pedidoInstance]
	}

	def update() {
		def pedidoInstance = Pedido.get(params.id)
		if (!pedidoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'pedido.label', default: 'Pedido'),
				params.id
			])
			redirect(action: "list")
			return
		}

		if (params.version) {
			def version = params.version.toLong()
			if (pedidoInstance.version > version) {
				pedidoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'pedido.label', default: 'Pedido')]
						as Object[],
						"Another user has updated this Pedido while you were editing")
				render(view: "edit", model: [pedidoInstance: pedidoInstance])
				return
			}
		}
		if(pedidoInstance.items.size()<1){
			flash.error = message(code: 'default.updated.error', args: [
				pedidoInstance.id
			])
			redirect(action: "show", id: pedidoInstance.id)
			return 
		}
			
		def prevSeña = pedidoInstance.seña
		pedidoInstance.properties = params
		if(prevSeña == null && pedidoInstance.seña != null) {
			pedidoInstance.estado = EstadoPedido.Señado
		}

		if (!pedidoInstance.save(flush: true)) {
			render(view: "edit", model: [pedidoInstance: pedidoInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'pedido.label', default: 'Pedido'),
			pedidoInstance.id
		])
		redirect(action: "show", id: pedidoInstance.id)
	}

	def delete() {
		def pedidoInstance = Pedido.get(params.id)
		if (!pedidoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'pedido.label', default: 'Pedido'),
				params.id
			])
			redirect(action: "list")
			return
		}

		try {
			pedidoInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [
				message(code: 'pedido.label', default: 'Pedido'),
				params.id
			])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [
				message(code: 'pedido.label', default: 'Pedido'),
				params.id
			])
			redirect(action: "show", id: params.id)
		}
	}
}
