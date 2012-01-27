package sieym

import org.joda.time.DateTime;
import org.joda.time.Duration
import org.joda.time.Interval;

class PedidoGeneralService {

	def planificar(Pedido pedido) {
//		pedido.estado=EstadoPedido.Planificado
		
	
		
		def sobrantesDisponibles=Sobrante.findAllByDisponible(true)
		println "check sobrante"
		//se pregunta por cada sobrante si existe algun producto disponible 
		sobrantesDisponibles.each {
				//se pasa a no disponible se le asgna el pedido y paso a setear el sobrante 
				it.disponible=false
				it.pedido=pedido
				Integer sobra=it.cantidad
				boolean disponible=true
				new Sobrante(producto:it.producto,cantidad:cant).save(flush:true)
				pedido.items.each {ItemPedido e
					if(e.producto==it.producto){
						Integer capacidad=e.paquete.capacidad*e.cantidad
						if(disponible){
							if(sobra>=capacidad){
								e.producto.cantidad=0
								it.save()
								disponible=false
							}else{
								Double resto=sobra/e.paquete.capacidad
								Integer ctdad=resto-e.producto.cantidad
								e.producto.cantidad-=ctdad
							}
						}
						
						
					}
				}
				
				
				println "Hay sobrantes " + it.producto
		}
		
		
	}
	
}
