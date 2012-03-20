
<%@ page import="auctionhaus.Listing" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'listing.label', default: 'Listing')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-listing" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-listing" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list listing">

                <!--L-1: The detail page for the listing shows the name of the listing-->
				<g:if test="${listingInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="listing.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${listingInstance}" field="name"/></span>
					
				</li>
				</g:if>

                <!--L-2: The detail page for the listing shows the starting bid price of the listing-->
                <g:if test="${listingInstance?.priceStarted}">
                    <li class="fieldcontain">
                        <span id="priceStarted-label" class="property-label"><g:message code="listing.priceStarted.label" default="Price Started" /></span>

                        <span class="property-value" aria-labelledby="priceStarted-label"><g:fieldValue bean="${listingInstance}" field="priceStarted"/></span>

                    </li>
                </g:if>

            <!--L-3: The detail page for the listing shows the most recent bid -->
                <g:if test="${listingInstance?.biddings}">
                    <li class="fieldcontain">
                        <span id="biddings-label" class="property-label"><g:message code="listing.biddings.label" default="Most Recent Bid" /></span>

                        <span class="property-value" aria-labelledby="">${listingInstance.getWinningBidPrice()}</span>


                    </li>
                </g:if>

            <!--L-4: The detail page for the listing shows the end date/time of the listing -->
                <g:if test="${listingInstance?.dateEnded}">
                    <li class="fieldcontain">
                        <span id="dateEnded-label" class="property-label"><g:message code="listing.dateEnded.label" default="Date Ended" /></span>

                        <span class="property-value" aria-labelledby="dateEnded-label"><g:formatDate date="${listingInstance?.dateEnded}" /></span>

                    </li>
                </g:if>

            <!--L-5: The detail page for the listing optionally shows the description -->
				<g:if test="${listingInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="listing.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${listingInstance}" field="description"/></span>
					
				</li>
				</g:if>

            <!--L-6: The detail page for the listing shows only the user portion of the email address
             of the user who created the listing (e.g. “mike” if the email address is “mike@piragua.com”)-->
                <g:if test="${listingInstance?.seller}">
                    <li class="fieldcontain">
                        <span id="seller-label" class="property-label"><g:message code="listing.seller.label" default="Seller" /></span>

                        <span class="property-value" aria-labelledby="seller-label"><g:link controller="customer" action="show" id="${listingInstance?.seller?.id}">${listingInstance?.seller?.email?.split("@")[0].encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>
                <!--L-7: The detail page for the listing allows a new bid to be placed (unit test of the controller action that handles this requirement)-->
                <g:render template="addBids"/>
            </ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${listingInstance?.id}" />
					<g:link class="edit" action="edit" id="${listingInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
