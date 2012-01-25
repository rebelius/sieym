package sieym

import org.joda.time.Duration;

class Fase {

	String nombre
	Duration duracion
	
	
	
	static constraints = {
		nombre unique:true
	}
}
