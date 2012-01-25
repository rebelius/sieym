
		<div class="nav" role="navigation">
			<ul class="main">
				<li><a href="${createLink(uri: '/user')}"><g:message code="navigation.users"/></a></li>
				<li><a href="${createLink(uri: '/logistica')}"><g:message code="navigation.config"/></a></li>
				<li><a href="${createLink(uri: '/pedido')}"><g:message code="navigation.orders"/></a></li>
				<li><a href="${createLink(uri: '/logistic')}"><g:message code="navigation.delivery"/></a></li>
				<li><a href="${createLink(uri: '/consulta')}"><g:message code="navigation.contact"/></a></li>
				<li><a href="${createLink(uri: '/stock')}"><g:message code="navigation.stock"/></a></li>
				<li><a href="${createLink(uri: '/estadistica')}"><g:message code="navigation.stats"/></a></li>
				<li><a href="${createLink(controller: 'auth', action: 'dologout')}"><g:message code="navigation.logout"/></a></li>
			</ul>
		</div>