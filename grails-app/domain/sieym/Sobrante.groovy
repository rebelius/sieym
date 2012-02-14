package sieym


class Sobrante {
	Maquina maquina
	Producto producto
	int cantidad
	
	static constraints = {
		maquina nullable: true
		producto nullable: false
		cantidad nullable:false,min:1
	}
}
