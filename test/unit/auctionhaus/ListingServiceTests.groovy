package auctionhaus



import grails.test.mixin.*
import org.junit.*
import grails.test.mixin.domain.DomainClassUnitTestMixin

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ListingService)
@TestMixin(DomainClassUnitTestMixin)
@Mock([Listing,Customer,Bidding])
class ListingServiceTests {

    //SRV-3: Create a Grails service method that supports creating a new bid for a listing (unit test)
    void testaddBidToListing() {
        def seller = ((new Customer(email: "ujjwal76@gmail.com", password: "abcdef"))).save(flush:true)
        assert 0 == seller.errors.errorCount
        def listing = (new Listing(name: "Coke", description:"It's coke", dateEnded: new Date()+50, priceStarted: 10, seller: seller)).save(flush: true)
        assert listing.errors.errorCount == 0
        def bidder = ((new Customer(email: "ujjwal77@gmail.com", password: "abcdef"))).save(flush:true)
        assert 0 == bidder.errors.errorCount
        def biddingInstance = new Bidding(bidAmount: 600, bidder: bidder, listing: listing)
        service.addBidToListing(biddingInstance)
        assert 1 == listing.biddings.size()
        def biddingInstance1 = new Bidding(bidAmount: 700, bidder: bidder, listing: listing)
        service.addBidToListing(biddingInstance1)
        assert 2 == listing.biddings.size()
        def biddingInstance2 = new Bidding(bidAmount: 750, bidder: bidder, listing: listing)
        service.addBidToListing(biddingInstance2)
        assert 3 == listing.biddings.size()
        
    }

    //SRV-2: Create a Grails service method that supports creating a new listing (unit test)
    void testaddNewListing() {
        def seller = new Customer(email: "ujjwal77@gmail.com", password: "abcdef")
        def listing = (new Listing(name: "TV", description:"It's TV", dateEnded: new Date()+100, priceStarted: 500, seller: seller))
        int id = service.createNewListing(listing)
        assert id == 1
        def listing1 = (new Listing(name: "PS3", description:"Gaming Console", dateEnded: new Date()+100, priceStarted: 299, seller: seller))
        int id1 = service.createNewListing(listing1)
        assert id1 == 2
    }
}

