package sieym

class Paquete  implements Comparable {
	String name
	String descripcion
	float capacidad
	int tiempoArmado

	public String toString() {
		"Tipo ${this.name} (${this.capacidad} Tn)"
	}
	@Override
	int compareTo(obj) {
		id.compareTo(obj.id)
	}
}

