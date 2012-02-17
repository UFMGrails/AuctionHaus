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

        //B-6: When a Listing is saved, any new Bids added for the listing must be saved (integration test)
        def seller = ((new Customer(email:"seller@gmail.com", password:"abcdef")).save(flush:true))
        def bidder = new Customer(email:"bidder@gmail.com", password:"abcdef").save(flush:true)
        def listing = (new Listing(name:"TV",dateEnded: futureDate,priceStarted: 1.50,seller: seller)).save(flush: true)
        //Clear any bidding rows if exists
        Bidding.findAllByListing(listing).each{it.delete()}
        assert 0 == Bidding.findAllByListing(listing).size();
        //insert new bidding rows
        listing.addToBiddings(new Bidding(bidAmount:2.00,bidder: bidder))
        listing.addToBiddings(new Bidding(bidAmount:2.50,bidder: bidder))
        try
        {
        listing.save(flush: true)
        }
        catch(Exception e)
        {
        println e
        }
        println(Bidding.findAllByListing(listing).size())
        assert 2 == Bidding.where {listing==listing}.count()
        

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
    }
}
