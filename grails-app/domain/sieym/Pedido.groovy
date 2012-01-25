package sieym

import org.joda.time.Duration;

class Pedido {

    static constraints = {
		fechaEntrega nullable: true
		motivoRechazo nullable: true
		motivoCancelado nullable: true
		seña min: 150d, nullable: true
		km nullable:false,min:1
		camion nullable:true
		createdDate  nullable:true
		lastModifiedDate nullable:true
    }
	
	static hasMany = [items: ItemPedido]
	Camion camion
	User cliente
	EstadoPedido estado
	Date fechaPedido
	Date fechaEntrega
	String direccionEntrega
	Double seña
	String motivoRechazo
	String motivoCancelado
	boolean recibirAvisos
	Integer km
	List items
	Date createdDate
	Date lastModifiedDate
	def beforeInsert = {
		createdDate = new Date()
	}
	def beforeUpdate = {
		lastModifiedDate = new Date()
	}
	public float calcularCoeficienteProduccion(Fase fase) {
		items.sum({it.producto.calcularCoeficienteProduccion(fase)})
	}
	
	public Duration calcularDuracion(Maquina mq){
		long d = mq.fase.duracion.millis * mq.rendimiento * this.calcularCoeficienteProduccion(mq.fase)
		new Duration(d)
	}
	
	public Duration calcularDuracion(List<Maquina> mqs){
		mqs.collect({calcularDuracion it}).max()
	}
	
	public float calcularPesoTotal(){
		this.items.sum({it.paquete.capacidad * it.cantidad})
	}
	
	public float calcularPesoTotal(Map sobrantes){
		this.items.sum({ (it.paquete.capacidad * it.cantidad) - (sobrantes[it.producto] ?: 0) })
	}
	
	public def generarAlternativasProduccion(List maquinas, Map sobrantes) {
		float peso = this.calcularPesoTotal(sobrantes);
		def simples = maquinas.findAll({it.capacidad >= peso})
		def compuestas = Maquina.agrupar(maquinas.findAll({it.capacidad < peso}), peso)
		(simples.collect({[it]}) + compuestas).collect({[maquinas: it, duracion: calcularDuracion(it)]}).sort({it.duracion})
	}
	
}
