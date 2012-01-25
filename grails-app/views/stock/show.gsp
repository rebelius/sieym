
<%@ page import="sieym.Paquete" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'paquete.label', default: 'Paquete')}" />
		<title><g:message code="default.stock.label" args="[entityName]" /></title>
	</head>
	<body>
	<div class="nav side" role="navigation">
	
	</div>
		<div id="show-paquete" class="content scaffold-show" role="main">
			<h1><g:message code="default.stock.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list paquete">
			
				<g:if test="${paqueteInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="paquete.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${paqueteInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${paqueteInstance?.descripcion}">
				<li class="fieldcontain">
					<span id="descripcion-label" class="property-label"><g:message code="paquete.descripcion.label" default="Descripcion" /></span>
					
						<span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${paqueteInstance}" field="descripcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${paqueteInstance?.capacidad}">
				<li class="fieldcontain">
					<span id="capacidad-label" class="property-label"><g:message code="paquete.capacidad.label" default="Capacidad" /></span>
					
						<span class="property-value" aria-labelledby="capacidad-label"><g:fieldValue bean="${paqueteInstance}" field="capacidad"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${paqueteInstance?.tiempoArmado}">
				<li class="fieldcontain">
					<span id="tiempoArmado-label" class="property-label"><g:message code="paquete.tiempoArmado.label" default="Tiempo de Armado" /></span>
					
						<span class="property-value" aria-labelledby="tiempoArmado-label"><g:fieldValue bean="${paqueteInstance}" field="tiempoArmado"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${paqueteInstance?.id}" />
					<g:actionSubmit id="señar" class="edit" value="Actualizar Stock" />
				</fieldset>
			</g:form>
		</div>
		
	<div id="señaDlg" style="display: none;" title="Ingrese Nuevo Stock">
		<ul id="error" class="errors" style="display: none;" role="alert">
			<li>Ingrese Nuevo Stock</li>
		</ul>
		<g:form>
			<g:hiddenField name="id" value="${paqueteInstance?.id}" />
			<g:field type="number" name="cantidad" value="${fieldValue(bean: pedidoInstance, field: 'cantidad')}"/>
			<g:actionSubmit id="enviar" class="edit" action="updateStock" value="Actualizar" />
		</g:form>
	</div>
<g:javascript>

$(document).ready(function() {
	$('#señar').click(function() {
	    $('#señaDlg').dialog({modal:true});
	    return false;
	});
	$('#enviar').click(function() {
	    if($('#cantidad').val() < 0) {
	    	$('#error').show();
		    return false;
	    }
	    return true;
	});
});
</g:javascript>
	</body>
</html>
