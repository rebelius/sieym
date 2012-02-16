import sieym.MateriaPrima;

import java.util.List;

import org.joda.time.DateTime
import org.joda.time.Duration

import sieym.ComponenteProducto;
import sieym.EstadoPedido;
import sieym.Fase
import sieym.ItemPedido;
import sieym.Logistica;
import sieym.Maquina
import sieym.MateriaPrima;
import sieym.Pedido;
import sieym.Producto;
import sieym.Role;
import sieym.Paquete;
import sieym.Sobrante

import sieym.User


class BootStrap {

	def fases

	def init = { servletContext ->
		def admin = User.findByUsername('admin') ?: new User(
				name: "Admin User",
				dni: 66666666,
				username: "admin",
				email: "admin@sieym.com",
				phone: 45555555,
				address: "Street 1000",
				password: "shhhh",
				active: true,
				role: Role.ADMIN
				);
		admin.save()
		println admin.errors
		def user = User.findByUsername('user') ?: new User(
				name: "user",
				dni: 555555555,
				username: "user",
				email: "user@sieym.com",
				phone: 45555555,
				address: "Street 2000",
				password: "hola",
				active: true,
				role: Role.USER
				)
		user.save()
		println user.errors
		def operador = User.findByUsername('operador') ?: new User(
				name: "operador",
				dni: 555555555,
				username: "operador",
				email: "user@sieym.com",
				phone: 45555555,
				address: "Street 2000",
				password: "hola",
				active: true,
				role: Role.OPERATOR
				)
		operador.save()
		println operador.errors
		
		new Logistica(precioPorKm: 250, tiempoPorKm: 1.5).save()

		Fase sapecado = Fase.findByNombre('Sapecado') ?: new Fase(nombre: "Sapecado", duracion: Duration.standardHours(9))
		Fase canchado = Fase.findByNombre('Canchado') ?: new Fase(nombre: "Canchado", duracion: Duration.standardHours(10))
		Fase molienda =  Fase.findByNombre('Molienda') ?:new Fase(nombre: "Molienda", duracion: Duration.standardHours(10))
		def fases = [sapecado, canchado, molienda]
		fases*.save()
		this.fases = fases

		Maquina mq1 =  Maquina.findByDescripcion('MQ_SAP_60') ?:new Maquina(descripcion: "MQ_SAP_60", fase: sapecado, capacidad: 601, rendimiento: 0.6)
		Maquina mq2 = Maquina.findByDescripcion('MQ_SAP_120') ?:new Maquina(descripcion: "MQ_SAP_120", fase: sapecado, capacidad: 120, rendimiento: 0.72)
		Maquina mq3 = Maquina.findByDescripcion('MQ_SAP_50') ?:new Maquina(descripcion: "MQ_SAP_50", fase: sapecado, capacidad: 50, rendimiento: 0.82)

		Maquina mq4 = Maquina.findByDescripcion('MQ_CAN_100') ?:new Maquina(descripcion: "MQ_CAN_100", fase: canchado, capacidad: 1010, rendimiento: 0.6)
		Maquina mq5 = Maquina.findByDescripcion('MQ_CAN_120') ?:new Maquina(descripcion: "MQ_CAN_120", fase: canchado, capacidad: 120, rendimiento: 0.72)
		Maquina mq7 = Maquina.findByDescripcion('MQ_CAN_80') ?:new Maquina(descripcion: "MQ_CAN_80", fase: canchado, capacidad: 80, rendimiento: 0.82)

		Maquina mq6 = Maquina.findByDescripcion('MQ_MOL_150') ?:new Maquina(descripcion: "MQ_MOL_150", fase: molienda, capacidad: 1150, rendimiento: 0.82)
		Maquina mq8 = Maquina.findByDescripcion('MQ_MOL_90') ?:new Maquina(descripcion: "MQ_MOL_90", fase: molienda, capacidad: 90, rendimiento: 0.88)
		Maquina mq9 = Maquina.findByDescripcion('MQ_MOL_60') ?:new Maquina(descripcion: "MQ_MOL_60", fase: molienda, capacidad: 60, rendimiento: 0.75)

		def maquinas = [mq1, mq2, mq3, mq4, mq5, mq6, mq7, mq8, mq9]
		maquinas*.save()

		MateriaPrima mp1 =  MateriaPrima.findByNombre('MP1') ?:this.createMateriaPrima("MP1", ['12', '25', '32'])
		MateriaPrima mp2 = MateriaPrima.findByNombre('MP2') ?:this.createMateriaPrima("MP2", ['13', '26', '9'])
		MateriaPrima mp3 =MateriaPrima.findByNombre('MP3') ?: this.createMateriaPrima("MP3", ['15', '21', '25'])
		MateriaPrima mp4 = MateriaPrima.findByNombre('MP4') ?:this.createMateriaPrima("MP4", ['11', '28', '19'])
		[mp1, mp2, mp3, mp4]*.save(flush: true)
		println mp1.errors

		Producto pA =  Producto.findByNombre('Producto A') ?:new Producto(nombre: "Producto A")

		Producto pB = Producto.findByNombre('Producto B') ?:new Producto(nombre: "Producto B")
		
		def productos = [pA, pB]
		productos*.save()
		ComponenteProducto css=ComponenteProducto.findByProductoAndMateriaPrima(pA,mp1)?:new ComponenteProducto(producto:pA,porcentaje: 25, materiaPrima: mp1)
		ComponenteProducto css1=ComponenteProducto.findByProductoAndMateriaPrima(pB,mp2)?:new ComponenteProducto(producto:pA,porcentaje: 25, materiaPrima: mp2)
		css.save(flush:true)
		css1.save(flush:true)
		
		
		
		
		MateriaPrima sal = MateriaPrima.findByNombre('Sal') ?: new MateriaPrima(nombre: 'Sal', descripcion: 'Para salar').save()
		MateriaPrima pimienta = MateriaPrima.findByNombre('Pimienta') ?: new MateriaPrima(nombre: 'Pimienta', descripcion: 'Para pimentar').save()

		Paquete paq =  Paquete.findByName('A') ?: new Paquete(name: "A", descripcion: "Paquete tipo A", capacidad: 23,cantidad:20, tiempoArmado: Duration.standardMinutes(30))
		paq.save()

		Paquete paq2 =Paquete.findByName('B') ?:  new Paquete(name: "B", descripcion: "Paquete tipo B", capacidad: 10,cantidad:20, tiempoArmado: Duration.standardMinutes(20))
		paq2.save()

	

//		def items = [new ItemPedido(producto: pA, paquete: paq2, cantidad: 10)]
//		Pedido ped = new Pedido(cliente: user, items: items, estado: EstadoPedido.Señado, fechaPedido: new Date(), direccionEntrega: "Formosa 1234")
//		ped.save()
	}

	def destroy = {
	}

	private MateriaPrima createMateriaPrima(String nombre, List cpValues) {
		Map cp = [(this.fases[0].nombre): cpValues[0],
					(this.fases[1].nombre): cpValues[1],
					(this.fases[2].nombre): cpValues[2]]
		MateriaPrima mp = new MateriaPrima(nombre: nombre)
		mp.coeficienteProduccion = cp
		return mp
	}

}
