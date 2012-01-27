package sieym


class Sobrante {
	Pedido pedido
	Producto producto
	int cantidad
	boolean disponible
	
	def beforeInsert = {
		disponible = true
	}
	static constraints = {
		pedido nullable: true
		producto nullable: false
		cantidad nullable:false,min:1
	}
}
