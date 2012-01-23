<g:applyLayout name="main">
	<head>
		<meta name="layout" content="main">
		<title><g:layoutTitle default="SIEYM"/></title>
		<g:layoutHead/>
	</head>
	<body>
		<div class="nav side" role="navigation">
			<ul>
				<li><a href="${createLink(uri: '/estadistica/listProducto')}"><g:message code="default.estadistica.by.product.label"/></a></li>
				<li><a href="${createLink(uri: '/estadistica/listPeriodo')}"><g:message code="default.estadistica.by.periodo.label"/></a></li>
				<li><a href="${createLink(uri: '/estadistica/listCliente')}"><g:message code="default.estadistica.by.cliente.label"/></a></li>
				<li><a href="${createLink(uri: '/estadistica/estadisticasInterna')}"><g:message code="default.estadistica.interna.label"/></a></li>
				<li><a href="${createLink(uri: '/estadistica/list')}"><g:message code="default.estadistica.by.operator.label"/></a></li>
			</ul>
		</div>
		<g:layoutBody/>
	</body>
</g:applyLayout>
