package auctionhaus

import org.springframework.dao.DataIntegrityViolationException

class ListingController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def listingService

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
        def listingInstance = Listing.list(params).findAll {it -> it.dateEnded >= new Date()}


        [listingInstanceList: listingInstance, listingInstanceTotal: Listing.count()]
    }


    //L-7: The detail page for the listing allows a new bid to be placed
    // (unit test of the controller action that handles this requirement)
    //L-8: Validation errors will be displayed on the listing detail page if an added bid does not pass
    // validation (unit test of the controller action that handles this requirement)
    def addBids = {

        def customerInstance = Customer.findByEmail("ujjwal77@gmail.com")
        def biddingInstance = new Bidding(params)
        biddingInstance.bidder = customerInstance

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

    def save() {
        def listingInstance = new Listing(params)
        if (!listingInstance.save(flush: true)) {
            render(view: "create", model: [listingInstance: listingInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'listing.label', default: 'Listing'), listingInstance.id])
        redirect(action: "show", id: listingInstance.id)
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
