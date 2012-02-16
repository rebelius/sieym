
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
			<div class="errors" role="status">
				${flash.error}
			</div>
		</g:if>
		<g:elseif test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:elseif>
		<g:else>
			<h3 style="margin-top: 10px;color:blue">
				Peso Total:
				${pedido.calcularPesoTotal()}
				Tn
			</h3>
			<h3 style="margin-top: 10px;color:blue">
				SALIDA DE PRODUCCIÓN:
				<strong><joda:format value="${plan.fechaPedidoTerminado}" pattern="dd/MM/yyyy - hh:mm:ss" /></strong>
			</h3>
			<h2 style="margin-top: 10px;">Cronograma de Producción</h2>


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
					<g:each in="${plan.fases}" var="fase" status="i">
					
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							<td>${fase.nombre}</td>

							<td>${plan.reservas[(fase)]?.maquinas?:'TIEMPO DE EMPAQUETADO'}</td>

							<td><joda:format value="${plan.reservas[(fase)].intervalo.start}" pattern="dd/MM/yyyy - hh:mm:ss" /></td>
							
							<td><joda:format value="${plan.reservas[(fase)].intervalo.end}" pattern="dd/MM/yyyy - hh:mm:ss" /></td>

						</tr>

					</g:each>
				</tbody>
			</table>

			<p style="margin: 10px;">
				TIEMPO DE EMPAQUETADO=<strong>${plan.tiempoEmpaquetado.standardSeconds} Segundos </strong>
			
			</p>

		</g:else>
	</div>
</body>
</html>
