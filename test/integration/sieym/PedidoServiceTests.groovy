package sieym

import org.joda.time.Duration;

class PedidoServiceTests {

	PedidoService pedidoService

	void testGenerarReservasPorFase() {

		Fase sapecado = new Fase(nombre: "Sapecado", duracion: Duration.standardHours(9))
		Fase canchado = new Fase(nombre: "Canchado", duracion: Duration.standardHours(10))
		def fases = [sapecado, canchado]
		fases*.save(flush: true)

		Maquina mq1 = new Maquina(descripcion: "MQ1", fase: sapecado, capacidad: 60, rendimiento: 0.6)
		Maquina mq2 = new Maquina(descripcion: "MQ2", fase: sapecado, capacidad: 180, rendimiento: 0.72)
		Maquina mq3 = new Maquina(descripcion: "MQ3", fase: canchado, capacidad: 240, rendimiento: 0.82)
		Maquina mq4 = new Maquina(descripcion: "MQ4", fase: canchado, capacidad: 50, rendimiento: 0.69)
		def maquinas = [(sapecado): [mq1, mq2], (canchado): [mq3, mq4]]
		maquinas.each {it.value*.save(flush: true)}

		def user1 = new User(
				name: "user",
				dni: 555555555,
				username: "user",
				email: "user@sieym.com",
				phone: "4555-4444",
				address: "Street 2000",
				password: "hola",
				active: true,
				role: Role.USER
				).save(flush: true)
		Date now = new Date()
		Producto pA = new Producto(nombre: "pA").save(flush: true)
		Producto pB = new Producto(nombre: "pB").save(flush: true)
		Paquete paq1 = new Paquete(name: "paq1", descripcion: "Paq 1", capacidad: 1, tiempoArmado: Duration.standardMinutes(5))
		paq1.save(flush: true)
		println paq1.errors
		Pedido pedido = new Pedido(cliente: user1, direccionEntrega: "lalala",
				estado: EstadoPedido.Solicitado, fechaPedido: new Date(), km: 100)
		pedido.addToItems(new ItemPedido(producto: pA, paquete: paq1, cantidad: 45))
		pedido.addToItems(new ItemPedido(producto: pB, paquete: paq1, cantidad: 23))
		pedido.save(flush: true)
		println pedido.errors

		this.pedidoService.generarReservasPorFase(pedido, fases)
	}
}
