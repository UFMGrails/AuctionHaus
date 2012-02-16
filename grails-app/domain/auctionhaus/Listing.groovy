package auctionhaus

class Listing {
    String name
    Date endDateTime
    BigDecimal startingPrice
    String description
    Customer seller
    Customer winner

    static hasMany = [biddings: Bidding]
    static belongsTo = [seller: Customer]

    static transients = ['winningBidPrice']

    static constraints = {
        // L1 and L6 name cannot be blank and needs to be less than 64 characters
        name(nullable:false, blank:false, size: 1..64)
        //L4 description can be blank but cannot be more than 256 characters
        description(nullable:true, blank: true, size:1..256)
        //L1 and L5 end date cannot be null. It needs to be a date sometime in the future.
        endDateTime(nullable:false,validator:{it > new Date()}  )
        //L1 starting Price cannot be null. Probably should not be negative
        startingPrice(nullable: false, validator: {it > 0.00})
        //L3 Listing is required to have a seller.
        seller (nullable: false)
        winner (nullable: true)
    }

    BigDecimal getWinningBidPrice(){
        def winningBidPrice
        if (biddings)
        {
            winningBidPrice = Bidding.createCriteria().get(){
            eq ("listing",this)
            projections { max "bidAmount"}
        }
        }

            if (winningBidPrice)
            {winningBidPrice}
            else if (priceStarted){priceStarted}
        else {0.0}
    }

}




