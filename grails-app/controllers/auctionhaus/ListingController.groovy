package auctionhaus

import org.springframework.dao.DataIntegrityViolationException

class ListingController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def listingService

    //S-2: Viewing the main landing page and the listing detail page does not require that the user be logged in
    //S-3: Bidding on an item requires that the user be logged in
    def beforeInterceptor = [action:this.&auth, except:["index", "list", "show"]]

    def auth() {
        if(!session.user) {
            redirect(controller:"customer", action:"login")
            return false
        }
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {



        //M-2: The main landing page shows up to 5 listings at a time
        params.max = Math.min(params.max ? params.int('max') : 5, 100)

        //M-1: The main landing page shows listings sorted by the date they were created (most recent first)
        //If params is not already sorted or ordered
        params.sort = (params.sort?params.sort:"dateCreated")
        params.order = (params.order?params.order:"desc")

        // M-4: Only listings with a end date/time that is in the future are visible on the main page
        def listingInstance = Listing.findAllByDateEndedGreaterThan(new Date(), params)


        [listingInstanceList: listingInstance, listingInstanceTotal: Listing.countByDateEndedGreaterThan(new Date())]
    }

    def mylisting() {
        //M-2: The main landing page shows up to 5 listings at a time
        //params.max = Math.min(params.max ? params.int('max') : 5, 100)

        //M-1: The main landing page shows listings sorted by the date they were created (most recent first)
        //If params is not already sorted or ordered
        params.sort = (params.sort?params.sort:"dateCreated")
        params.order = (params.order?params.order:"desc")

        // M-4: Only listings with a end date/time that is in the future are visible on the main page
        //def listingInstance = Listing.list(params).findAll{it -> it.seller.email == session.user.email}
        def listingInstance = Listing.findAllBySeller(session.user)
        [listingInstanceList: listingInstance, listingInstanceTotal: Listing.countBySeller(session.user)
        ]
    }


    //SRV-4: Refactor your Controllers from the previous assignments to use the service methods created in this section (unit test)
    //L-7: The detail page for the listing allows a new bid to be placed
    // (unit test of the controller action that handles this requirement)
    //L-8: Validation errors will be displayed on the listing detail page if an added bid does not pass
    // validation (unit test of the controller action that handles this requirement)
    def addBids = {

        //def customerInstance = Customer.findByEmail("ujjwal77@gmail.com")
        def biddingInstance = new Bidding(params)
        biddingInstance.bidder = session.user

            //def listingInstance = Listing.findById(params['listing.id'])
        try{
            listingService.addBidToListing(biddingInstance)
            flash.message = message(code: 'default.successful.bid.submitted', args: [message(code: 'listing.label', default: 'Listing'), params.id])

        }
        catch(Exception ex)
        {
            flash.message =  message(code: 'default.unsuccessful.bid.submitted', args: [message(code: 'listing.label', default: 'Listing'), params.id])

        }
        redirect(action: 'show', id: params['listing.id'])
    }




    def create() {
        [listingInstance: new Listing(params)]
    }

    //SRV-4: Refactor your Controllers from the previous assignments to use the service methods created in this section (unit test)
    def save() {

        if (session.user){
            params.seller = session.user
        }

        def listingInstance = new Listing(params)

        try{
            listingService.createNewListing(listingInstance)
            flash.message = message(code: 'default.created.message', args: [message(code: 'customer.label', default: 'Customer'), customerInstance.id])
            redirect(action: "mylisting", id: listingInstance.id)
        }catch(Exception e){
            flash.message =  message(code: 'default.create.listing.error', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            render(view: "create", model: [listingInstance: listingInstance])
        }
    }

    def show() {
        def listingInstance = Listing.get(params.id)
        if (!listingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            redirect(action: "list")
            return
        }

        [listingInstance: listingInstance]
    }

    def edit() {
        def listingInstance = Listing.get(params.id)
        if (!listingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            redirect(action: "list")
            return
        }

        [listingInstance: listingInstance]
    }

    def update() {
        def listingInstance = Listing.get(params.id)
        if (!listingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (listingInstance.version > version) {
                listingInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'listing.label', default: 'Listing')] as Object[],
                          "Another user has updated this Listing while you were editing")
                render(view: "edit", model: [listingInstance: listingInstance])
                return
            }
        }

        listingInstance.properties = params

        if (!listingInstance.save(flush: true)) {
            render(view: "edit", model: [listingInstance: listingInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'listing.label', default: 'Listing'), listingInstance.id])
        redirect(action: "show", id: listingInstance.id)
    }

    def delete() {
        def listingInstance = Listing.get(params.id)
        if (!listingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            redirect(action: "list")
            return
        }

        try {
            listingInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
