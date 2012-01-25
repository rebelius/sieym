package sieym

import org.joda.time.DateTime;

class SobranteServiceTests extends GroovyTestCase {

	SobranteService sobranteService
	Producto pA
	Producto pB

	void setUp() {
		Date now = new Date()
		this.pA = new Producto(nombre: "pA").save(flush: true)
		this.pB = new Producto(nombre: "pB").save(flush: true)
		new Sobrante(producto: this.pA, cantidad: 10, disponible: now + 1).save(flush: true)
		new Sobrante(producto: this.pA, cantidad: 15, disponible: now - 1).save(flush: true)
		new Sobrante(producto: this.pA, cantidad: 20, disponible: now - 1).save(flush: true)
		new Sobrante(producto: this.pB, cantidad: 10, disponible: now - 1).save(flush: true)
	}

	void testConsultarSobrantesDisponibles() {
		assert 35 == this.sobranteService.consultarSobrantesDisponibles(this.pA)
		assert 10 == this.sobranteService.consultarSobrantesDisponibles(this.pB)
	}

	void testUsarSobrantesDisponibles() {
		assert 35 == this.sobranteService.consultarSobrantesDisponibles(this.pA)
		this.sobranteService.usarSobrantesDisponibles(this.pA, 22)
		assert 13 == this.sobranteService.consultarSobrantesDisponibles(this.pA)
		this.sobranteService.usarSobrantesDisponibles(this.pA, 13)
		assert 0 == this.sobranteService.consultarSobrantesDisponibles(this.pA)
	}

	void testRegistrarSobrantes() {
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
		Paquete paq1 = new Paquete(name: "paq1", descripcion: "Paq 1", capacidad: 1F, tiempoArmado: 5).save(flush: true)
		Pedido pedido = new Pedido(cliente: user1, direccionEntrega: "lalala",
			estado: EstadoPedido.Solicitado, fechaPedido: new Date())
		pedido.addToItems(producto: pA, paquete: paq1, cantidad: 45)
		pedido.addToItems(producto: pB, paquete: paq1, cantidad: 23)
		pedido.save(flush: true)
		println pedido.errors

		this.sobranteService.registrarSobrantes(pedido, 85, new Date() + 3)
		Sobrante sobA = Sobrante.findByProductoAndDisponibleGreaterThan(this.pA, new Date() + 2)
		assertNotNull(sobA)
		assertEquals(11, sobA.cantidad)
		Sobrante sobB = Sobrante.findByProductoAndDisponibleGreaterThan(this.pB, new Date() + 2)
		assertNotNull(sobB)
		assertEquals(5, sobB.cantidad)
	}
}
