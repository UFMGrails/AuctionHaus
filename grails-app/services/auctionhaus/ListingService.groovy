package auctionhaus
import grails.validation.ValidationException
import javax.persistence.PersistenceException

class ListingService {

    //SRV-3: Create a Grails service method that supports creating a new bid for a listing (unit test)
    def addBidToListing(Bidding bidInstance) {

        def customerInstance = Customer.findByEmail("ujjwal77@gmail.com")

        /*if (customerInstance == null) {
            throw new Exception(customerInstance.errors.allErrors)
        }  */

       bidInstance.save(flush: true)

        if (!bidInstance.validate()) {
            throw new ValidationException("Bid is not valid",bidInstance.errors)
        }
        return bidInstance.id

    }

    //SRV-2: Create a Grails service method that supports creating a new listing (unit test)
    def createNewListing(Listing listing) {
        if (!listing.validate()) {
            throw new ValidationException("Customer is not valid",listing.errors)
        }   else if(!listing.save(flush: true)){
            throw new PersistenceException()
        }
        return listing.id
    }
}
