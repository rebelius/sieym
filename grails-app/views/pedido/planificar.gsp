
<%@ page import="sieym.Pedido"%>
<%@ page import="sieym.EstadoPedido"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="pedidos">
<g:set var="entityName"
	value="${message(code: 'pedido.label', default: 'Pedido')}" />
<title>Planificacion de Pedido</title>
</head>
<body>
	<div id="list-pedido" class="content scaffold-list" role="main">
		<h1>Planificacion de Pedido</h1>

		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<g:else>
			<h3 style="margin-top: 10px;">
				Peso Total:
				${pedido.calcularPesoTotal()}
				Tn
			</h3>
			<h2 style="margin-top: 10px;">Posibles Alternativas</h2>
			<g:if test="${flash.message}">
				<div class="message" role="status">
					${flash.message}
				</div>
			</g:if>
			<g:each in="${alt}" var="curAlt">
				<h3 style="margin-top: 10px;">
					${curAlt.key.nombre}
					(CP:
					${pedido.calcularCoeficienteProduccion(curAlt.key)})
				</h3>
				<ul style="margin-left: 30px;">
					<g:each var="op" status="i" in="${curAlt.value}">
						<li><strong>Maquinas:</strong> ${op.maquinas} <strong>Duracion:</strong>
							${op.duracion.standardHours}hs</li>
					</g:each>
				</ul>
			</g:each>
		</g:else>
	</div>
</body>
</html>
