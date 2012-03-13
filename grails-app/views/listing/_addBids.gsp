<div class="dialog" id="addBids">
    <div class="message" style="display:none;">
        <g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}"/>

        <g:hasErrors bean="${biddingInstance}">
            <div class="errors">
                <g:renderErrors bean="${biddingInstance}" as="list"/>
            </div>
        </g:hasErrors>
    </div>

    <g:form name="addBids" action="addBids" method="post">
        <g:hiddenField name="listing.id" value="${listingInstance?.id}"/>
        <div class="dialog">
            <table>
                <tbody>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="bidAmount"><g:message code="bidding.bidAmount" default="Amount"/>:</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: biddingInstance, field: 'bidAmount', 'errors')}">
                        <g:textField name="bidAmount" value="${fieldValue(bean:biddingInstance, field: 'bidAmount')}"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <g:submitButton name="bid" class="save"  value="${message(code: 'bid', default: 'Bid')}" />

    </g:form>
</div>