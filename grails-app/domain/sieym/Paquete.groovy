package sieym

class Paquete  implements Comparable {
	String name
	String descripcion
	float capacidad
	int tiempoArmado
	Integer cantidad

	
	public String toString() {
		"Tipo ${this.name} (${this.capacidad} Tn)"
	}
	
	static constraints = {
		cantidad(nullable: true)
		name(nullable: false)
		descripcion(nullable: true)
		capacidad(nullable: false)
		tiempoArmado(nullable: false)
	}
	@Override
	int compareTo(obj) {
		id.compareTo(obj.id)
	}
}

