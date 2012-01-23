package sieym

class Producto implements Comparable{
	
	static hasMany = [composicion: ComponenteProducto]

    static constraints = {
    }
	
	List composicion
	String nombre
	
	public float calcularCoeficienteProduccion(Fase fase) {
		composicion.sum({it.materiaPrima.coeficiente(fase) * (it.porcentaje / 100)})
	}
	@Override
	int compareTo(obj) {
		id.compareTo(obj.id)
	}
}
