
<%@ page import="sieym.Pedido"%>
<%@ page import="sieym.EstadoPedido"%>
<%@ page import="sieym.EstadisticaInternaCommand"%>

<!doctype html>
<html>
<head>
<meta name="layout" content="estadistica">
<g:set var="entityName"
	value="${message(code: 'pedido.label', default: 'Pedido')}" />
<title><g:message code="default.estadistica.interna.label" args="[entityName]" /></title>
</head>
<body>
	<div id="list-pedido" class="content scaffold-list" role="main">
		
		<h1>
			<g:message code="default.estadistica.interna.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<table>
			<thead>
				<tr>

					<th><g:message code="pedido.id.label" default="Estado" /></th>

					<th><g:message code="pedido.cliente.id.label"
							default="Cantidad Pedidos" /></th>


				</tr>
			</thead>
			<tbody>
				<g:each in="${pedidoInstanceList}" status="i" var="pedidoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td>
							
								${fieldValue(bean: pedidoInstance, field: "estado")}
						

						<td>
							${fieldValue(bean: pedidoInstance, field: "cantidad")}
						</td>

					
					</tr>
				</g:each>
			</tbody>
		</table>
	<br>
	</div>
	<br>
</body>
</html>
