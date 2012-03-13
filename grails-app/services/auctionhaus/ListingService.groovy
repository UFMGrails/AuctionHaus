package auctionhaus


class ListingService {

    def addBidToListing(Bidding bidInstance) {

        def customerInstance = Customer.findByEmail("ujjwal77@gmail.com")

        if (customerInstance == null) {
            throw new Exception(customerInstance.errors.allErrors)
        }

        bidInstance.save(failOnError:true)
        /*if (!bidInstance.validate()) {
            throw new Exception(bidInstance.errors.allErrors)
        } else if (!bidInstance.save(flush: true,failOnError: true)) {
            throw new Exception(bidInstance.errors.allErrors)
        } */
        return bidInstance.id

    }
}
