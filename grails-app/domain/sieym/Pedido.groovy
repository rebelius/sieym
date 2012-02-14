package sieym

import com.TotalProd
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
		fase nullable:true
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
	Fase fase
	def beforeInsert = {
		createdDate = new Date()
	}
	def beforeUpdate = {
		lastModifiedDate = new Date()
	}
	public float calcularCoeficienteProduccion(Fase fase) {
		this.items.sum({it.producto.calcularCoeficienteProduccion(fase)})
	}
	def getTotalToneladas(){
		Map<Producto,Integer>paquetesTotales= new TreeMap<Producto,Integer>();
		this.items.each {
			if(paquetesTotales.get(it.producto)!=null){
				println paquetesTotales.get(it.paquetepedido)
				Integer val=paquetesTotales.get(it.producto)
				val+=it.paquete.capacidad*it.cantidad
				paquetesTotales.put(it.producto, val)
				println paquetesTotales.get(it.producto)
			}else{
				paquetesTotales.put(it.producto,it.paquete.capacidad*it.cantidad)
			}
		}
		return paquetesTotales
		
	}
	public List<SobrantePedido> getSobrantePedido(){
		return SobrantePedido.findAllByPedido(this)
	}
	public Duration calcularDuracion(Maquina mq){
		long d = (mq.fase.duracion.millis * mq.rendimiento * this.calcularCoeficienteProduccion(mq.fase))/3240
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
	
	public Duration cacularTiempoEmpaquetado() {
		long totalMinutes = 0
		this.items.each {
			totalMinutes += it.paquete.tiempoArmado.getStandardMinutes() * it.cantidad
		}
		Duration.standardSeconds(totalMinutes)
	}
	
	public def generarAlternativasProduccion(List maquinas, Map sobrantes) {
		float peso = this.calcularPesoTotal(sobrantes);
		def simples = maquinas.findAll({it.capacidad >= peso})
		def compuestas = Maquina.agrupar(maquinas.findAll({it.capacidad < peso}), peso)
		(simples.collect({[it]}) + compuestas).collect({[maquinas: it, duracion: calcularDuracion(it)]}).sort({it.duracion})
	}
	
}
