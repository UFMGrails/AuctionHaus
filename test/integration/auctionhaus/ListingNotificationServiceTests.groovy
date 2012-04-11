package auctionhaus

import static org.junit.Assert.*
import org.junit.*
import grails.test.mixin.TestFor
import grails.test.mixin.Mock

@TestFor(ListingNotificationService)
@Mock(Listing)
class ListingNotificationServiceTests {
    def jmsService


    void testListingEndedSendJMS() {
        def seller = new Customer(email: "ujjwal55@gmail.com", password: "abcdef").save(flush:true)
        def listing1 = (new Listing(name: "Cup", description:"cup", dateEnded: new Date()-1,
                priceStarted: 10, seller: seller,notificationSent: false))
        listing1.addToBiddings(new Bidding(bidAmount: 15, bidder: seller)).save(flush: true, validate: false)
        //listing1.addToBiddings(new Bidding(bidAmount: 20, bidder: seller)).save(flush: true, validate: false)
        listing1.winner = seller
        listing1.save(validate: false, flush: true)
        //seller.save(flush: true)
        //print seller
        //listing1.notificationSent = false
        def srv = new ListingNotificationService()
        srv.listingEnded(listing1)
        print listing1.notificationSent
    }




}
