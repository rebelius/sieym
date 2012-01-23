
<%@ page import="sieym.Pedido"%>
<%@ page import="sieym.EstadoPedido"%>
<%@ page import="sieym.ProductoCommand"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="estadistica">
<g:set var="entityName"
	value="${message(code: 'pedido.label', default: 'Pedido')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	<div id="list-pedido" class="content scaffold-list" role="main">
	
		<h1>
			<g:message code="default.estadistica.by.product.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<table>
			<thead>
				<tr>

					<th><g:message code="producto.id.label" default="ID Producto" /></th>

					<th><g:message code="producto.name"
							default="Nombre Producto" /></th>

					<th><g:message code="producto.cantidad" default="Cantidad" /></th>


				</tr>
			</thead>
			<tbody>
				<g:each in="${pedidoInstanceList}" status="i" var="pedidoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td>${fieldValue(bean: pedidoInstance, field: "producto.id")}</td>

						<td>
							${fieldValue(bean: pedidoInstance, field: "producto.nombre")}
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
