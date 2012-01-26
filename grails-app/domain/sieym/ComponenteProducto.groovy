package sieym


import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder
import sieym.Producto
import sieym.MateriaPrima
class ComponenteProducto implements Serializable {
	
	Producto producto
	MateriaPrima materiaPrima
	Integer porcentaje
	boolean equals(other) {
		if (!(other instanceof ComponenteProducto)) {
			return false
		}

		other.producto?.id == producto?.id &&
			other.materiaPrima?.id == materiaPrima?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (producto) builder.append(producto.id)
		if (materiaPrima) builder.append(materiaPrima.id)
		builder.toHashCode()
	}
	static ComponenteProducto get(long productoId, long materiaPrimaId) {
		find 'from ComponenteProducto where producto.id=:productoId and materiaPrima.id=:materiaPrimaId',
			[productoId: productoId, materiaPrimaId: materiaPrimaId]
	}

	static ComponenteProducto create(Producto producto, MateriaPrima materiaPrima, boolean flush = false) {
		new ComponenteProducto(producto: producto, materiaPrima: materiaPrima).save(flush: flush, insert: true)
	}
	
	static ComponenteProducto create(Producto producto, MateriaPrima materiaPrima,Integer porcentaje, boolean flush = false) {
		new ComponenteProducto(producto: producto, materiaPrima: materiaPrima,porcentaje:porcentaje).save(flush: flush, insert: true)
	}
	static boolean remove(Producto producto, MateriaPrima materiaPrima, boolean flush = false) {
		ComponenteProducto instance = ComponenteProducto.findByProductoAndMateriaPrima(producto, materiaPrima)
		instance ? instance.delete(flush: flush) : false
	}

	static void removeAll(Producto producto) {
		executeUpdate 'DELETE FROM ComponenteProducto WHERE producto=:producto', [producto: producto]
	}

	static void removeAll(MateriaPrima materiaPrima) {
		executeUpdate 'DELETE FROM ComponenteProducto WHERE materiaPrima=:materiaPrima', [materiaPrima: materiaPrima]
	}

	static mapping = {
		id composite: ['materiaPrima', 'producto']
		version false
	}
}
