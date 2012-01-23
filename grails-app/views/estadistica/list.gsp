
<%@ page import="sieym.Pedido"%>
<%@ page import="sieym.EstadoPedido"%>
<%@ page import="sieym.PaqueteCommand"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="estadistica">
<g:set var="entityName"
	value="${message(code: 'pedido.label', default: 'Pedido')}" />
<title><g:message code="default.estadistica.by.operator.label" args="[entityName]" /></title>
</head>
<body>
	<div id="list-pedido" class="content scaffold-list" role="main">
	
		<h1>
			<g:message code="default.estadistica.by.operator.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<table>
			<thead>
				<tr>

					<th><g:message code="paquete.id.label" default="ID Paquete" /></th>

					<th><g:message code="paquete.name"
							default="Nombre Paquete" /></th>
					<th><g:message code="paquete.capacidad"
							default="Capacidad" /></th>

					<th><g:message code="producto.cantidad" default="Cantidad" /></th>


				</tr>
			</thead>
			<tbody>
				<g:each in="${pedidoInstanceList}" status="i" var="pedidoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td>${fieldValue(bean: pedidoInstance, field: "paquete.id")}</td>

						<td>
							${fieldValue(bean: pedidoInstance, field: "paquete.name")}
						</td>
						
						<td>
							${fieldValue(bean: pedidoInstance, field: "paquete.capacidad")}
						</td>

						<td>
							${fieldValue(bean: pedidoInstance, field: "cantidad")}
						</td>


					</tr>
				</g:each>
			</tbody>
		</table>
	</div>
</body>
</html>
