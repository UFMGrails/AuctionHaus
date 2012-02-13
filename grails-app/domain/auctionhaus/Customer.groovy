package auctionhaus

class Customer {

    String email
    String password
    Date dateCreated

    //Customer as many listing and Biddings.
    static hasMany = [listings: Listing,biddings: Bidding]
    static mappedBy = [listings: "seller", biddings: "bidder"]

    // /C-1: Customers have email address, password and created date fields (unit test)
    static constraints = {
        //C-4: Password must be between 6-8 characters (unit test)
        password(size:6..8, blank:  false)
        //C-3: Email address must be of a valid form (@.*) (unit test)
        //C-2: Email address must be a unique field (integration test)
        email(email:true,unique: true,blank: true)
    }
}
