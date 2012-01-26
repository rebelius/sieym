
		<div class="nav" role="navigation">
			<ul class="main">
			<g:if test="${session?.user?.role}">
				<g:if test="${session.user.role==sieym.Role.OPERATOR}">
					<li><a href="${createLink(uri: '/pedido')}"><g:message code="navigation.orders"/></a></li>
					<li><a href="${createLink(uri: '/logistic')}"><g:message code="navigation.delivery"/></a></li>
					<li><a href="${createLink(uri: '/stock')}"><g:message code="navigation.stock"/></a></li>
					<li><a href="${createLink(uri: '/estadistica')}"><g:message code="navigation.stats"/></a></li>
					<li><a href="${createLink(controller: 'auth', action: 'dologout')}"><g:message code="navigation.logout"/></a></li>
				</g:if>
				<g:elseif test="${session.user.role==sieym.Role.USER}">
					<li><a href="${createLink(uri: '/pedido')}"><g:message code="navigation.orders"/></a></li>
					<li><a href="${createLink(uri: '/consulta')}"><g:message code="navigation.contact"/></a></li>
					<li><a href="${createLink(controller: 'auth', action: 'dologout')}"><g:message code="navigation.logout"/></a></li>
				
				</g:elseif>
				<g:else>
					<li><a href="${createLink(uri: '/user')}"><g:message code="navigation.users"/></a></li>
					<li><a href="${createLink(uri: '/logistica')}"><g:message code="navigation.config"/></a></li>
					<li><a href="${createLink(uri: '/pedido')}"><g:message code="navigation.orders"/></a></li>
					<li><a href="${createLink(uri: '/logistic')}"><g:message code="navigation.delivery"/></a></li>
					<li><a href="${createLink(uri: '/consulta')}"><g:message code="navigation.contact"/></a></li>
					<li><a href="${createLink(uri: '/stock')}"><g:message code="navigation.stock"/></a></li>
					<li><a href="${createLink(uri: '/estadistica')}"><g:message code="navigation.stats"/></a></li>
					<li><a href="${createLink(controller: 'auth', action: 'dologout')}"><g:message code="navigation.logout"/></a></li>
				
				</g:else>
			</g:if>
			</ul>
		</div>