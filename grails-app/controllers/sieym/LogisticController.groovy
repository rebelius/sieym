package sieym

import org.springframework.dao.DataIntegrityViolationException
import sieym.EstadoPedido
class LogisticController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [camionInstanceList: Camion.list(params), camionInstanceTotal: Camion.count()]
    }
	
	def list1() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def pedidos = Pedido.findAllByEstado( EstadoPedido.Proceso)
		pedidos.each {
			it.estado= EstadoPedido.Embolsado
			it.save()
		}
//		Camion.list().each{
//		it.disponible=true
//		it.save()
//			
//		}
		[pedidoInstanceList: Pedido.findAllByEstado( EstadoPedido.Embolsado, params), pedidoInstanceTotal: Pedido.countByEstado( EstadoPedido.Embolsado),
			estado: EstadoPedido.Embolsado]
	
	}
	
	def list2() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		if(params?.id){
			[camionInstanceList: Camion.findAllByDisponible(true,params), camionInstanceTotal: Camion.countByDisponible(true),id:params?.id]
				
		}else{
		
			flash.message ="No se encontro El pedido por favor intentelo nuevamente"
			redirect(action: "list1")
		}
	}
	
    def asignar() {
        def camionInstance =  Camion.get(params?.idCamion)
        def pedidoInstance =  Pedido.get(params?.id)
		if(camionInstance?.disponible && pedidoInstance?.estado ==EstadoPedido.Embolsado){
			camionInstance.disponible=false
			pedidoInstance.estado =EstadoPedido.EnViaje
			pedidoInstance.camion=camionInstance
			pedidoInstance.save(flush:true)
			if(!pedidoInstance.save(flush:true) || !camionInstance.save(flush:true)){
				flash.error ="No se pudo asignar intentelo nuevamente"
				redirect(action: "list2", id: pedidoInstance.id)
				return;
			}else{
				flash.message ="Gracias por asignar el pedido"
				redirect(action: "list1")
				return;
			}
			
		}else{
				flash.error ="No se encontro el pedido intentelo nuevamete"
				redirect(action: "list2", id: pedidoInstance.id)
		
		
		}
		
    }

   
}
