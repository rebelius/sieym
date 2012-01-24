
<%@ page import="sieym.Pedido"%>
<%@ page import="sieym.EstadoPedido"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="estadistica">
<g:set var="entityName"
	value="${message(code: 'pedido.label', default: 'Pedido')}" />
<title><g:message code="default.estadistica.by.periodo.label" args="[entityName]" /></title>
</head>
<body>
	<div id="list-pedido" class="content scaffold-list" role="main">
		
		<h1>
			<g:message code="default.estadistica.by.periodo.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<table>
			<thead>
				<tr>

					<th><g:message code="pedido.id.label" default="ID Pedido" /></th>


					<th><g:message code="pedido.cliente.nombre.label" default="Cliente" /></th>
					<th><g:message code="pedido.cliente.nombre.label" default="Direccion Entrega" /></th>

					<g:sortableColumn property="estado"
						title="${message(code: 'pedido.estado.label', default: 'Estado')}" />

					<g:sortableColumn property="fechaPedido"
						title="${message(code: 'pedido.fechaPedido.label', default: 'Fecha Pedido')}" />


				</tr>
			</thead>
			<tbody>
				<g:each in="${pedidoInstanceList}" status="i" var="pedidoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td>
								${fieldValue(bean: pedidoInstance, field: "id")}
							</td>


						<td>
							${fieldValue(bean: pedidoInstance, field: "cliente.name")}
						</td>
						<td>
							${fieldValue(bean: pedidoInstance, field: "direccionEntrega")}
						</td>

						
						<td>
							${pedidoInstance.estado.name()}
						</td>

						<td><g:formatDate date="${pedidoInstance.fechaPedido}"
								format="dd/MM/yyyy" /></td>

					
							<td><g:formatDate date="${pedidoInstance.fechaEntrega}"
									format="dd/MM/yyyy" /></td>
						

					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<g:paginate total="${pedidoInstanceTotal}" />
		</div>
	</div>
</body>
</html>
