package sieym

import java.util.Set;

class Producto implements Comparable{
	
    static constraints = {
    }
	String nombre
	
	public float calcularCoeficienteProduccion(Fase fase) {
		getCoeficiente().sum({it.materiaPrima.coeficiente(fase) * (it.porcentaje / 100)})
	}
	
	Set<MateriaPrima> getCoeficiente() {
		ComponenteProducto.findAllByProducto(this).collect { it.materiaPrima } as Set
	}
	Set<ComponentesCommand> getCoeficienteTotal() {
		ComponenteProducto.findAllByProducto(this).collect { new ComponentesCommand(producto:it.producto,materiaPrima:it.materiaPrima ,porcentaje:it.porcentaje )} as Set
	}
	@Override
	int compareTo(obj) {
		id.compareTo(obj.id)
	}
}
