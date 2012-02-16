
<%@ page import="sieym.Pedido"%>
<%@ page import="sieym.EstadoPedido"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="mainLR">
<g:set var="entityName"
	value="${message(code: 'pedido.label', default: 'Pedido')}" />
<title><g:message code="default.consulta.label" args="[entityName]" /></title>
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
			<g:message code="default.consulta.label" args="[entityName]" />
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
					<g:sortableColumn property="fase"
						title="${message(code: 'pedido.fase.label', default: 'Fase')}" />
					<g:sortableColumn property="tiempo"
						title="${message(code: 'pedido.fase.label', default: 'Tiempo estimado a Produccion')}" />


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
								format="dd/MM/yyyy" /></td>
						<td>${pedidoInstance.fase.nombre}</td>
						<td>&nbsp;</td>
					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<g:paginate total="${pedidoInstanceTotal}" params="${params }"/>
		</div>
		
		<br>
		<br>
		<br>
		<table>
			<thead>
				<tr style="font-size: 15px">
					<th >Total Fases Produccion</th>
					
				</tr>
			</thead>
			<tbody>
				<tr class="odd">
					<td>
						<g:each in="${faseInstanceList}" status="i" var="faseInstance">
							
							<strong style="color: blue"> 
							${fieldValue(bean: faseInstance, field: "nombre")}</strong>  --
							
						</g:each><strong style="color: blue"> Empaquetado</strong> 	
					</td>
				
				</tr>
			
			</tbody>
		</table>
		<br>
		<br>
		<br>
	</div>
</body>
</html>
