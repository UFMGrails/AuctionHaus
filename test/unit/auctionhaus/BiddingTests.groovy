package auctionhaus



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Bidding)
//@Mock(Listing)
class BiddingTests {
    def today = new Date()
    def futureDate = today+ 100
    def seller = new Customer(email:"seller@gmail.com", password:"abcdef")
    def bidder = new Customer(email:"bidder@gmail.com", password:"abcdef")
    def listing = new Listing(name:"TV",dateEnded: futureDate,priceStarted: 1.50,seller: seller)

    protected void setUp()
    {
        super.setUp()

    }


    void testAmountIsRequired()
    {
        //B-1: Bids have the following required fields: amount and date/time of bid (unit test)
        def bidding = new Bidding(dateCreated: new Date())
        bidding.validate()
       assert "nullable" == bidding.errors["bidAmount"].code
    }

    void testDateCreatedIsAssigned()
    {
        //B-1: Bids have the following required fields: amount and date/time of bid (unit test)
        def bidding = new Bidding(bidAmount:2.50,listing:listing,bidder: bidder)
        bidding.validate()
        assert 0 == bidding.errors.fieldErrorCount
    }

    void testBidsAreRequiredToHaveABidder()
    {
        //B-3: Bids are required to have a bidder (Customer) (unit test)
        def bidding = new Bidding(bidAmount:2.50,listing:listing)
        bidding.validate()
        assert "nullable" == bidding.errors["bidder"].code
    }


    void testBidsAreRequiredToBeForListing()
    {
        //B-2: Bids are required to be for a Listing (unit test)
        def bidding = new Bidding(bidAmount:2.50, bidder: bidder)
        bidding.validate()
        assert "nullable" == bidding.errors["listing"].code
    }


    void testListingHasListOfBids()
    {
        //B-4: A Listing has a list of Bids for that Listing (unit test)
       mockDomain(Listing)
        listing.addToBiddings( new Bidding(bidAmount:2.00,bidder: bidder, listing:listing))
        listing.addToBiddings( new Bidding(bidAmount:2.50,bidder: bidder, listing:listing))
        listing.addToBiddings( new Bidding(bidAmount:3.50,bidder: bidder, listing:listing))
        listing.save(flush:true)

        listing.refresh()
        assert 3 == listing.biddings.size()

    }


}
