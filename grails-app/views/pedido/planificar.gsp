
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

		<g:if test="${flash.error}">
			<div class="error" role="status">
				${flash.error}
			</div>
		</g:if>
		<g:elseif test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:elseif>
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
						<th>Maquinas</th>
						<th>Entrada</th>
						<th>Salida</th>

					</tr>
				</thead>
				<tbody>
					<g:each in="${fases}" var="fase" status="i">
					
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

							<td>${fase.nombre}</td>

							<td>${reservas[(fase)].maquinas}</td>

							<td><joda:format value="${reservas[(fase)].intervalo.start}" pattern="dd/MM/yyyy - hh:mm" /></td>
							
							<td><joda:format value="${reservas[(fase)].intervalo.end}" pattern="dd/MM/yyyy - hh:mm" /></td>

						</tr>

					</g:each>
				</tbody>
			</table>

			<p style="margin: 10px;">
				El tiempo de empaquetado del Pedido es de
				<strong>${tiempoEmpaquetado.standardHours}hs</strong> y el mismo estara listo para el
				dia
				<strong><joda:format value="${fechaPedidoTerminado}" pattern="dd/MM/yyyy - hh:mm" /></strong>
			</p>

		</g:else>
	</div>
</body>
</html>
