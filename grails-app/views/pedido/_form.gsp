<%@page import="sieym.Role"%>
<%@ page import="sieym.Pedido" %>
<%@ page import="sieym.Paquete" %>
<%@ page import="sieym.User" %>



<div class="fieldcontain ${hasErrors(bean: pedidoInstance, field: 'cliente', 'error')} required">
	<label for="cliente">
		<g:message code="pedido.cliente.label" default="Cliente" />
		<span class="required-indicator">*</span>
	</label>
	<g:if test="${session.user.role!=sieym.Role.USER}"> 
		<g:select name="cliente.id" from="${User.findAllByRole(sieym.Role.USER)}" required="" value="${pedidoInstance?.cliente?.id}" optionKey="id" optionValue="name"/>
	</g:if>
	<g:else>
		${session.user.name}
	</g:else>
</div>

<div class="fieldcontain ${hasErrors(bean: pedidoInstance, field: 'direccionEntrega', 'error')} ">
	<label for="direccionEntrega">
		<g:message code="pedido.direccionEntrega.label" default="Direccion Entrega" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="direccionEntrega" required="" value="${pedidoInstance?.direccionEntrega}"/>
</div>

	
<div class="fieldcontain ${hasErrors(bean: pedidoInstance, field: 'direccionEntrega', 'error')} ">
	<label for="direccionEntrega">
		<g:message code="pedido.direccionEntrega.label" default="Distancia Km" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="km" required="" value="${pedidoInstance?.km}"/>
</div>
	



