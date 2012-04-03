<g:if test="${bidInstanceList}">
    <table>
        <thead>
        <tr>
            <th><g:message code="bidding.bidder" default="Bidder"/></th>
            <th><g:message code="bid.dateCreated" default="Date Created"/></th>
            <th><g:message code="bid.amount" default="Amount"/></th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${bidInstanceList}" status="i" var="bidInstance">
            <tr>
                <td>${bidInstance.bidder}</td>
                <td><g:formatDate date="${bidInstance.dateCreated}"/></td>
                <td>$<g:formatNumber number="${fieldValue(bean: bidInstance, field: 'bidAmount')}" currencyCode="USD" format="#,##0.00"/></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</g:if>