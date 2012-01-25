
<%@ page import="sieym.Pedido"%>
<%@ page import="sieym.EstadoPedido"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'pedido.label', default: 'Pedido')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:if test="${flash.error}">
			<div class="error" role="status">${flash.error}</div>
			</g:if>
	<div class="nav side" role="navigation">
			<ul>
				<li><a href="${createLink(uri: '/logistic')}"><g:message code="default.camiones.label"/></a></li>
				<li><a href="${createLink(uri: '/logistic/list1')}"><g:message code="default.enviar.pedido.label"/></a></li>
			</ul>
		</div>
	<br>
	<div id="list-pedido" class="content scaffold-list" role="main">
		
		<table>
			<thead>
				<tr>

					<th><g:message code="pedido.id.label" default="ID Pedido" /></th>

					<th><g:message code="pedido.cliente.nombre.label" default="Nombre" /></th>
					<th><g:message code="pedido.cliente.id.label"
							default="Dni" /></th>
					<th><g:message code="pedido.cliente.id.label"
							default="Distancia km" /></th>
					
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
							<g:link action="list2" id="${pedidoInstance.id}">
								${fieldValue(bean: pedidoInstance, field: "id")}
							</g:link>
						
						</td>

						<td>
							${fieldValue(bean: pedidoInstance, field: "cliente.name")}
						</td>
						<td>
							${fieldValue(bean: pedidoInstance, field: "cliente.dni")}
						</td>
						<td>
							${fieldValue(bean: pedidoInstance, field: "km")}
						</td>
						<g:if test="${pedidoInstance?.estado== EstadoPedido.Solicitado }">
						<td>
							<g:each in="${	pedidoInstance?.items}" status="i1" var="ps">
							<p>${ps?.producto?.nombre } -${ps?.cantidad }</p>
							</g:each>
						
						</td>
						</g:if>

						<td>
							${pedidoInstance.estado.name()}&nbsp;
							<g:if test="${params.chooseEstado
								&& pedidoInstance.estado != EstadoPedido.Rechazado
								&& pedidoInstance.estado != EstadoPedido.Entregado}">
								<g:link action="cambiarEstado" id="${pedidoInstance.id}">[cambiar]</g:link>
							</g:if>
							
						</td>

						<td><g:formatDate date="${pedidoInstance.fechaPedido}"
								format="dd/MM/yyyy" /></td>

						

					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<g:paginate total="${pedidoInstanceTotal}" params="${params }"/>
		</div>
	</div>
</body>
</html>
