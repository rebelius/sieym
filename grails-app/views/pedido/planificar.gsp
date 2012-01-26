
<%@ page import="sieym.Pedido"%>
<%@ page import="sieym.EstadoPedido"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="pedidos">
<g:set var="entityName"
	value="${message(code: 'pedido.label', default: 'Pedido')}" />
<title>Planificacion de Pedido</title>
</head>
<body>
	<div id="list-pedido" class="content scaffold-list" role="main">
		<h1>Planificacion de Pedido</h1>

		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<g:else>
			<h3 style="margin-top: 10px;">
				Peso Total:
				${pedido.calcularPesoTotal()}
				Tn
			</h3>
			<h2 style="margin-top: 10px;">Cronograma de Reservas</h2>


			<table>
				<thead>
					<tr>

						<th>Fase</th>
						<th>Maquina</th>
						<th>Entrada</th>
						<th>Salida</th>

					</tr>
				</thead>
				<tbody>
					<g:each in="${fases}" var="fase">
						<g:each in="${reservas[fase].reservas}" var="res" status="i">

							<tr class="${(j % 2) == 0 ? 'even' : 'odd'}">

								<td>${fase.nombre}</td>
	
								<td>${res.key.descripcion}</td>

								<td>${res.value.intervalo.start}</td>
								
								<td>${res.value.intervalo.end}</td>

							</tr>

						</g:each>
					</g:each>
				</tbody>
			</table>

			<p>
				El tiempo de empaquetado del Pedido es de
				${tiempoEmpaquetado.standardHours}hs y el mismo estara listo para el
				dia
				${fechaPedidoTerminado}
			</p>

		</g:else>
	</div>
</body>
</html>
