package sieym

import org.springframework.dao.DataIntegrityViolationException

class PedidoController {


	def pedidoGeneralService
	def mailService
	def index() {
		redirect(action: "list", params: params)
	}

	def planificar() {
		Pedido pedido = Pedido.get(params.id)
		
		Map<Paquete,Integer>paquetesTotales= new TreeMap<Paquete,Integer>();
		pedido.items.each {
			if(paquetesTotales.get(it.paquete)!=null){
				println paquetesTotales.get(it.paquete)
				Integer val=paquetesTotales.get(it.paquete)
				val+=it.cantidad
				paquetesTotales.put(it.paquete, val)
				println paquetesTotales.get(it.paquete)
			}else{
				paquetesTotales.put(it.paquete, it.cantidad)
			}
		}
		def paquetes=Paquete.list()
		boolean cantidad=true
		paquetes.each {
			if(paquetesTotales.get(it) <=it.cantidad){
				println "cantidad feliz cantidad total del pedido "+paquetesTotales.get(it)+" nombre fabrica "+it.name+" cantidad total de la fabrica"+it.cantidad
			}	else{
				if(flash?.error){
					flash.error += "<li>Stock Insuficiente Del tipo de paquete "+it.name+" , faltan  "+(paquetesTotales.get(it)-it.cantidad)+" paquetes</li>"
				}else{
					flash.error = "<li>Stock Insuficiente Del tipo de paquete "+it.name+" , faltan  "+(paquetesTotales.get(it)-it.cantidad)+" paquetes</li>"
				
				}
				println "cantidad triste cantidad total del pedido "+paquetesTotales.get(it)+" nombre fabrica "+it.name+" cantidad total de la fabrica"+it.cantidad
			}
			
		}
		if(flash?.error){
			redirect(action: "show", id: pedido.id)
			return
			
		}
		
		if(pedido.items){
			try {
				def plan = pedidoGeneralService.planificar(pedido)
				return [pedido: pedido,plan:plan] 
			} catch (IllegalStateException e) {
				flash.error = e.getMessage()
				return [pedido: pedido]
			}
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
			EstadoPedido estado = EstadoPedido.Planificado
			Pedido.findAllByEstado(estado, params).each{
//			it.estado=EstadoPedido.Entregado
//			it.save(flush:true)
			}
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
		
//		pedidoInstance.estado=sieym.EstadoPedido.Señado
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
			try{
				mailService.sendMail {
					to session.user.email
					from conf.ui.register.emailFrom
					subject conf.ui.register.emailSubject
					html "Su pedido Numero" +pedidoInstance+" a sido aceptado.Muchas Gracias"
				}
			}catch (Exception e){
				println  "No se pudo enviar el mail"
			
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
			
			
			try{
				mailService.sendMail {
					to session.user.email
					from conf.ui.register.emailFrom
					subject conf.ui.register.emailSubject
					html "Su pedido Numero" +pedidoInstance+" a sido Rechazado.Por Favor Contactese con la Empresa Muchas Gracias"
				}
			}catch (Exception e){
				println "No se pudo enviar el mail"
			
			}
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
