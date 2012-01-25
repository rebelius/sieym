package sieym

import org.joda.time.Duration;

class Paquete  implements Comparable {
	String name
	String descripcion
	int capacidad
	Duration tiempoArmado
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

