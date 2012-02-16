package com


import sieym.EstadoPedido
import sieym.Logistica;
import sieym.Maquina;
import sieym.Producto;
import sieym.ReservaMaquina
import sieym.RealPedido
import sieym.SobrantePedido
import grails.converters.JSON
import sieym.RealPedido
import sieym.Sobrante
import sieym.Pedido
import sieym.Fase
import org.joda.time.DateTime;
class EstimacionPlanificacionJob {
    def timeout = 5000l // execute job once in 5 seconds

    def execute() {
		println "----------------"
       	def dat=Calendar.getInstance().getTime() 
		ReservaMaquina.findAllByStartLessThan( dat).each {
			println it as JSON
			def pedido=it.pedido
			if(it?.maquina){
				def maquina=it.maquina
				pedido.fase=maquina.fase
				pedido.estado=EstadoPedido.Produccion
				def sob=SobrantePedido.findAllByPedido(it.pedido)
				Map<Producto,Integer>productosTotalesPorTonelada=(TreeMap<Producto,Integer>)pedido.getTotalToneladas()
				sob.each {
					def prf=it.producto
					def can=it.cantidad
					productosTotalesPorTonelada.put(prf, productosTotalesPorTonelada.get(prf)-can)
				}
				int ton= RealPedido.findAllByPedido(pedido).sum({it.cantidad	})
				
				int sobrante=it.maquina.capacidad-ton
				if (sobrante>0 &&ton!=0){
					println "hay sobrante"
					productosTotalesPorTonelada.each() { key, value ->
						println ton
						println value
						println sobrante
						int total=(value/ton)*sobrante
						def sob1=Sobrante.findByProducto(key)?:new Sobrante(maquina:it.maquina,producto:key)
						if(sob1?.cantidad){
							sob1.cantidad+=total
						}else{
							sob1.cantidad=total
						}
						sob1.save(flush:true)
						println sob1 as JSON
					}
				}
			}else{
				pedido.fase=null
				pedido.estado=EstadoPedido.Embolsado
			}

			pedido.save(flush:true)
			
		}
		ReservaMaquina.findAllByEndLessThan( dat).each {
		
			if(!it?.maquina){
				def pedido=it.pedido
				pedido.estado=EstadoPedido.Listo
				pedido.save(flush:true)
			}
			it.delete()
		}
		
		println "----------------"
    }
}
