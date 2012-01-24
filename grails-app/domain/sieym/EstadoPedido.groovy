package sieym;

public enum EstadoPedido {
	Solicitado,
	Aceptado,
	Señado,
	Entregado,
	Planificado,
	Produccion,
	Embolsado,
	EnViaje,
	Rechazado,
	Cancelado,
	Proceso
	public static List valuesFor(EstadoPedido ep) {
		def values = [ep]
		values.add Cancelado
		if(ep == Aceptado) {
			values.add Solicitado
		}
		return values
	}
}
