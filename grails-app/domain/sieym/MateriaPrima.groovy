package sieym

class MateriaPrima {

    static constraints = {
		coeficienteProduccion nullable: true
		descripcion nullable: true
    }

	Map coeficienteProduccion
	String nombre
	String descripcion

	public String toString() {
		nombre
	}
	
	public coeficiente(Fase fase) {
		this.coeficienteProduccion[fase.nombre] as Float
	}
}
