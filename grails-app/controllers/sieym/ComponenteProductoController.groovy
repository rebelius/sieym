package sieym

import org.springframework.dao.DataIntegrityViolationException
import sieym.Producto
import sieym.MateriaPrima
class ComponenteProductoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "GET"]

    def index() {
        redirect(action: "create", params: params)
    }

    def create() {
		
		[ productoId: params.productoId]
    }

    def save() {
        Producto productoInstance = Producto.get(params.productoId)
		Integer porcentaje=Integer.valueOf(params?.porcentaje);
		MateriaPrima materia =MateriaPrima.get(params.materiaPrima.id)
		if (!productoInstance.coeficiente.contains(materia)) {
			ComponenteProducto.create productoInstance, materia,porcentaje
		}else{
			flash.error="No se pueden agregar 2 veces el mismo componente"
			render(view: "create", model: [ productoId: params.productoId])
			return
		
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'componenteProducto.label', default: 'ComponenteProducto')])
        redirect(controller: "producto", action: "show", id: productoInstance.id)
    }

    def edit() {
        def componenteProductoInstance = ComponenteProducto.get(params.id)
        if (!componenteProductoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'componenteProducto.label', default: 'ComponenteProducto'), params.id])
            redirect(action: "_list")
            return
        }

        [componenteProductoInstance: componenteProductoInstance, productoId: params.productoId]
    }

    def update() {
        def componenteProductoInstance = ComponenteProducto.get(params.id)
        if (!componenteProductoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'componenteProducto.label', default: 'ComponenteProducto'), params.id])
            redirect(action: "_list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (componenteProductoInstance.version > version) {
                componenteProductoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'componenteProducto.label', default: 'ComponenteProducto')] as Object[],
                          "Another user has updated this ComponenteProducto while you were editing")
                render(view: "edit", model: [componenteProductoInstance: componenteProductoInstance])
                return
            }
        }

        componenteProductoInstance.properties = params

        if (!componenteProductoInstance.save(flush: true)) {
            render(view: "edit", model: [componenteProductoInstance: componenteProductoInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'componenteProducto.label', default: 'ComponenteProducto'), componenteProductoInstance.id])
        redirect(controller: "producto", action: "show", id: params.productoId)
    }

    def delete() {
		Producto productoInstance = Producto.get(params.productoId)
		Long id=Long.valueOf(params.id);
		MateriaPrima materia =MateriaPrima.get(id)
		if (productoInstance.coeficiente.contains(materia)) {
			ComponenteProducto.remove productoInstance, materia
		}
		
        redirect(controller: "producto", action: "show", id: params.productoId)
        return
        
    }
}
