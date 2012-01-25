package sieym

import org.joda.time.DateTime;
import org.joda.time.Interval;

class ReservaMaquina {

	static belongsTo = Maquina
	static transients = ['intervalo']

	Pedido pedido
	Date start
	Date end

	Interval getIntervalo() {
		if(this.start && this.end) {
			return new Interval(this.start.getTime(), this.end.getTime())
		} else {
			return null
		}
	}

	void setIntervalo(Interval interval) {
		this.start = interval.start.toDate()
		this.end = interval.end.toDate()
	}
}
