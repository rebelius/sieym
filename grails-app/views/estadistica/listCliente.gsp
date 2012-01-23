
<%@ page import="sieym.Pedido"%>
<%@ page import="sieym.EstadoPedido"%>
<%@ page import="sieym.ClienteCommand"%>


<!doctype html>
<html>
<head>
<meta name="layout" content="estadistica">
<g:set var="entityName"
	value="${message(code: 'pedido.label', default: 'Pedido')}" />
<title><g:message code="default.estadistica.by.cliente.label" args="[entityName]" /></title>
</head>
<body>
	<div id="list-pedido" class="content scaffold-list" role="main">

		<h1>
			<g:message code="default.estadistica.by.cliente.label" args="[entityName]"  />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<table>
			<thead>
				<tr>

					<th><g:message code="user.id.label" default="ID Usuario" /></th>

					<th><g:message code="user.address.label"
							default="Direccion" /></th>

					<th><g:message code="dni" default="Dni" /></th>
					<th><g:message code="nombre" default="nombre" /></th>
					<th><g:message code="telefono" default="telefono" /></th>
					<th><g:message code="username" default="username" /></th>
					
					<th><g:message code="cantidad" default="cantidad" /></th>


				</tr>
			</thead>
			<tbody>
				<g:each in="${pedidoInstanceList}" status="i" var="pedidoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td>
								${fieldValue(bean: pedidoInstance, field: "cliente.id")}
						</td>

						<td>
							${fieldValue(bean: pedidoInstance, field: "cliente.address")}
						</td>

						
						<td>
							${fieldValue(bean: pedidoInstance, field: "cliente.dni")}
						</td>
						<td>
							${fieldValue(bean: pedidoInstance, field: "cliente.name")}
						</td>
						<td>
							${fieldValue(bean: pedidoInstance, field: "cliente.phone")}
						</td>
						<td>
							${fieldValue(bean: pedidoInstance, field: "cliente.username")}
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
