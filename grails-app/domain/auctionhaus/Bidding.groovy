package auctionhaus

class Bidding {
    BigDecimal bidAmount
    Date dateCreated

    //B-1: Bids have the following required fields: amount and date/time of bid (unit test)
    //B-2: Bids are required to be for a Listing (unit test)
    //B-3: Bids are required to have a bidder (Customer) (unit test)
    static belongsTo = [bidder:Customer, listing:Listing]

    static mapping = {
        listing cascade: 'refresh'
    }

    static constraints = {

        //Bidding dateCreated shall be less than listing dateEnded
        dateCreated(validator:
                {val,obj ->
                    if (obj.listing)
                        val <= obj.listing.dateEnded
                }
        )
        
        //Bidders can not be sellers. That would be cheating
        bidder(Validator:
                {val,obj ->
                    if (obj.listing)
                        obj.listing.seller == !val
                })

        /*B-2: Bids are required to be for a Listing (unit test). Instance of Bid can not have
        nullable listings and bidders*/
        //B-3: Bids are required to have a bidder (Customer) (unit test)
        
        bidAmount(nullable: false)

        //B-5: The Bid amount must be at least .50 higher than the previous Bid for the same listing (integration test)
       bidAmount( validator:
                {val, obj ->
                    if (obj.listing) {
                    val >= (obj.listing.winningBidPrice+0.50)}}
                    )





}  }
