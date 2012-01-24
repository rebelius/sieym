package sieym

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class EstadisticaController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "listProducto", params: params)
	}

	def list() {
			
		EstadoPedido estado = EstadoPedido.Entregado
		def pedidos=Pedido.findAllByEstado(estado)
		Map<Paquete,Integer> prod= new TreeMap<Paquete, Integer>()
		pedidos.each{
			it.items.each{
				if(prod.get(it.paquete)!=null){
					Integer f=prod.get(it.paquete)+1
					prod.put(it.paquete, f)
				}else{
					prod.put(it.paquete, 1)
				}
			}
		}
		def l= new ArrayList<PaqueteCommand>()
		for(Map.Entry<Paquete,Integer> entry : prod.entrySet()) {
			Paquete key = entry.getKey();
			Integer value = entry.getValue();
			l.add(new PaqueteCommand(paquete:key,cantidad:value))
		  }
		  
		[pedidoInstanceList: l]
	}
	
	def listCliente() {
		def usrs= User.findAllByRole(Role.USER)
		def l= new ArrayList<ClienteCommand>()
		usrs.each {
			Integer caunt=Pedido.countByClienteAndEstado(it,EstadoPedido.Entregado)
			if(caunt>0){
				l.add(new ClienteCommand(cliente:it ,cantidad:caunt))
			}
		}
		
		[pedidoInstanceList:l]
	
	}

	
	def listPedido() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[pedidoInstanceList: Pedido.findAllByEstado(EstadoPedido.Entregado, params), pedidoInstanceTotal: Pedido.countByEstado(EstadoPedido.Entregado)]
		
	}

	
	def listProducto() {
		
		EstadoPedido estado = EstadoPedido.Entregado
		def pedidos=Pedido.findAllByEstado(estado)
		Map<Producto,Integer> prod= new TreeMap<Producto, Integer>()
		pedidos.each{
			it.items.each{
				if(prod.get(it.producto)!=null){
					Integer f=prod.get(it.producto)+1
					prod.put(it.producto, f)
				}else{
					prod.put(it.producto, 1)
				}
			}
		}
		def l= new ArrayList<ProductoCommand>()
		for(Map.Entry<Producto,Integer> entry : prod.entrySet()) {
			Producto key = entry.getKey();
			Integer value = entry.getValue();
			l.add(new ProductoCommand(producto:key,cantidad:value))
		  }
		  
		[pedidoInstanceList: l]
		
	}

	
	def estadisticasInterna() {
		def l = new ArrayList<EstadisticaInternaCommand>()
		def c = Pedido.createCriteria()
		def results = c.list {
		   projections {
			  groupProperty("estado")
			  rowCount()
		   }
		}
		
		 results.each {
			 l.add(new EstadisticaInternaCommand( estado: it[0],cantidad: it[1]))
		 }
		 
		[pedidoInstanceList:l]
			
		
			
		
	}

	

}


public class ProductoCommand{
	Producto producto
	Integer cantidad
}
public class PaqueteCommand{
	Paquete paquete
	Integer cantidad
}
public class EstadisticaInternaCommand{
	String estado
	Long cantidad
}
public class ClienteCommand{
	User cliente
	Integer cantidad
}
