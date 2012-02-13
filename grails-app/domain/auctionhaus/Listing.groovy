package auctionhaus

class Listing {
    String name
    Date dateEnded
    BigDecimal priceStarted
    Customer winner
    Customer seller

    static hasMany = [biddings: Bidding]
    static belongsTo = [seller: Customer]

    static transients = ['winningBidPrice']


    static constraints = {
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




