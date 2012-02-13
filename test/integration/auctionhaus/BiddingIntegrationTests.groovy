package auctionhaus

import static org.junit.Assert.*
import org.junit.*

class BiddingIntegrationTests {
    def today = new Date()
    def futureDate = today+ 100

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testBiddingIsSavedWhenListingIsSaved()
    {
        //B-3: Bids are required to have a bidder (Customer) (unit test)

        def seller = ((new Customer(email:"seller@gmail.com", password:"abcdef")).save(flush:true))
        def bidder = new Customer(email:"bidder@gmail.com", password:"abcdef").save(flush:true)
        def listing = (new Listing(name:"TV",dateEnded: futureDate,priceStarted: 1.50,seller: seller)).save(flush: true)

        listing.addToBiddings(new Bidding(bidAmount:2.00,bidder: bidder))
        listing.addToBiddings(new Bidding(bidAmount:2.50,bidder: bidder))
        listing.save(flush: true)        
       assert 2 == Bidding.where {listing == listing}.count()

    }

    @Test
    void testBidAmountIsFiftyCentHigherThanPreviousBid() {
        //B-5: The Bid amount must be at least .50 higher than the previous Bid for the same listing (integration test)
        def seller = ((new Customer(email:"seller@gmail.com", password:"abcdef")).save(flush:true))
        def bidder = new Customer(email:"bidder@gmail.com", password:"abcdef").save(flush:true)
        def listing = (new Listing(name:"TV",dateEnded: futureDate,priceStarted: 1.50,seller: seller)).save(flush: true)
        new Bidding(bidAmount:listing.winningBidPrice+0.50,bidder: bidder, listing:listing).save(flush: true)
        listing.refresh()
        assert !(new Bidding(bidAmount:listing.winningBidPrice+0.49,bidder: bidder, listing:listing).validate())
        assert (new Bidding(bidAmount:listing.winningBidPrice+0.51,bidder: bidder, listing:listing).validate())    
        println listing.winningBidPrice

    }
}
