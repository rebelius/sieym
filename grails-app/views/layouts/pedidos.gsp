<g:applyLayout name="main">
	<head>
		<meta name="layout" content="main">
		<title><g:layoutTitle default="SIEYM"/></title>
		<g:layoutHead/>
	</head>
	<body>
		<div class="nav side" role="navigation">
			<ul>
			
			<g:if test="${session.user.role==sieym.Role.OPERATOR}">
				<li><a href="${createLink(uri: '/pedido/list?estado=Solicitado')}"><g:message code="default.pedido.validar.label"/></a></li>
				<li><a href="${createLink(uri: '/pedido/list?estado=Aceptado')}"><g:message code="default.pedido.señar.label"/></a></li>
				<li><a href="${createLink(uri: '/pedido/list?estado=Señado')}"><g:message code="default.pedido.planificar.label"/></a></li>
				<li><a href="${createLink(uri: '/pedido/list?chooseEstado=1')}"><g:message code="default.pedido.actualizarestado.label"/></a></li>
			</g:if>
				<g:elseif test="${session.user.role==sieym.Role.USER}">
				<li><a href="${createLink(uri: '/pedido/create')}"><g:message code="default.pedido.registrar.label"/></a></li>
			
				</g:elseif>
				<g:else>
				<li><a href="${createLink(uri: '/pedido/create')}"><g:message code="default.pedido.registrar.label"/></a></li>
				<li><a href="${createLink(uri: '/pedido/list?estado=Solicitado')}"><g:message code="default.pedido.validar.label"/></a></li>
				<li><a href="${createLink(uri: '/pedido/list?estado=Aceptado')}"><g:message code="default.pedido.señar.label"/></a></li>
				<li><a href="${createLink(uri: '/pedido/list?estado=Señado')}"><g:message code="default.pedido.planificar.label"/></a></li>
				<li><a href="${createLink(uri: '/pedido/list?chooseEstado=1')}"><g:message code="default.pedido.actualizarestado.label"/></a></li>
				
				</g:else>
				
			</ul>
		</div>
		<g:layoutBody/>
	</body>
</g:applyLayout>
