package auctionhaus
import grails.validation.ValidationException

class ListingService {

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
}
