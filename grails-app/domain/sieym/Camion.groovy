package sieym

class Camion {

    static constraints = {
    }
	
	static belongsTo = Logistica
	
	String marca
	Integer modelo
	String patente
	String chofer
	boolean disponible
	
	def beforeInsert = {
		disponible=true
	}
}
