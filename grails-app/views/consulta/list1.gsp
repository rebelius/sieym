
<%@ page import="sieym.Pedido"%>
<%@ page import="sieym.EstadoPedido"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="mainLR">
<g:set var="entityName"
	value="${message(code: 'pedido.label', default: 'Pedido')}" />
<title>Pedidos En Viaje</title>

</head>
<body>

		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:if test="${flash.error}">
			<div class="errors" role="status">${flash.error}</div>
			</g:if>
	<div class="nav side" role="navigation">
			<ul>
				<li><a href="${createLink(uri: '/consulta')}">Pedidos en Produccion </a></li>
				<li><a href="${createLink(uri: '/consulta/list1')}">Pedidos en Viaje</a></li>
			</ul>
		</div>


	<div id="list-pedido" class="content scaffold-list" role="main">
		<div class="nav" role="navigation">

			
			<g:if test="${params.chooseEstado}">
				<g:form action="list" method="GET" style="display: inline;">
					<span> <g:message code="pedido.id.label" default="Estado" />
					</span>
					<g:select name="estado" from="${EstadoPedido?.values()}"
						keys="${EstadoPedido.values()*.name()}" value="${params.estado}"
						onchange="this.form.submit();" noSelection="${['':'Todo...']}"/>
						<g:hiddenField name="chooseEstado" value="1"/>
				</g:form>
			</g:if>

		</div>
		<h1>
		Listado de Pedidos en Viaje
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

					<th><g:message code="pedido.cliente.nombre.label" default="Nombre" /></th>
					<th><g:message code="pedido.cliente.id.label"
							default="Dni" /></th>

					<g:sortableColumn property="estado"
						title="${message(code: 'pedido.estado.label', default: 'Estado')}" />

					<g:sortableColumn property="fechaPedido"
						title="${message(code: 'pedido.fechaPedido.label', default: 'Fecha Pedido')}" />
					<g:sortableColumn property="tiempo"
						title="${message(code: 'pedido.fase.label', default: 'Tiempo de Llegada Destino')}" />


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
							${fieldValue(bean: pedidoInstance, field: "cliente.dni")}
						</td>
						

						<td>
							${pedidoInstance.estado.name()}
							
						</td>

						<td><g:formatDate date="${pedidoInstance.fechaPedido}"
								format="dd/MM/yyyy - hh:mm:ss" /></td>
						<td><g:formatDate date="${pedidoInstance.fechaEntrega}"
								format="dd/MM/yyyy - hh:mm:ss" /></td>
					</tr>
				</g:each>
				<g:each in="${ped}" status="i" var="sssd">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td>
					
								${fieldValue(bean: sssd, field: "id")}
						
							
						</td>

						<td>
							${fieldValue(bean: sssd, field: "cliente.name")}
						</td>
						<td>
							${fieldValue(bean: sssd, field: "cliente.dni")}
						</td>
						

						<td>
							${sssd.estado.name()}
							
						</td>

						<td><g:formatDate date="${sssd.fechaPedido}"
								format="dd/MM/yyyy - hh:mm:ss" /></td>
						<td></td>
						<td><g:formatDate date="${sssd.fechaEntrega}"
								format="dd/MM/yyyy - hh:mm:ss" /></td>
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
