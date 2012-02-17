package com

import sieym.EstadoPedido
import sieym.Logistica;
import org.joda.time.DateTime;
import sieym.Pedido
class TravelJob {
    def timeout = 1000l // execute job once in 1 seconds

    def execute() {
		def dat= new Date()
		def log=Logistica.get(1)
		
		def da1te =new DateTime()
		Pedido.findAllByFechaEntregaLessThan(da1te.toDate() ).each {
			if(it.estado==EstadoPedido.EnViaje){
				Long l= it.km*log.tiempoPorKm
				def d= it.lastModifiedDate.getTime()
				def ff=d +l
				
				if(ff<dat.getTime()){
					println "--------------------------"
					println "Se proceso el registro " +it
					println "--------------------------"
					it.estado =EstadoPedido.Entregado
					it.camion.disponible=true
					it.save(flush:true)
					
				}
			}
		
		}
		
		
		
        // execute task
    }
}
