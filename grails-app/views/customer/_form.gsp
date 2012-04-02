<%@ page import="auctionhaus.Customer" %>



<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="customer.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="email" name="email" required="" value="${customerInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="customer.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:passwordField name="password" maxlength="8" required="" value="${customerInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'biddings', 'error')} ">
	<label for="biddings">
		<g:message code="customer.biddings.label" default="Biddings" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${customerInstance?.biddings?}" var="b">
    <li><g:link controller="bidding" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="bidding" action="create" params="['customer.id': customerInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'bidding.label', default: 'Bidding')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'listings', 'error')} ">
	<label for="listings">
		<g:message code="customer.listings.label" default="Listings" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${customerInstance?.listings?}" var="l">
    <li><g:link controller="listing" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="listing" action="create" params="['customer.id': customerInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'listing.label', default: 'Listing')])}</g:link>
</li>
</ul>

</div>

