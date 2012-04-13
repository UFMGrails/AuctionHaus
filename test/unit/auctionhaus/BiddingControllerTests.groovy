package auctionhaus



import org.junit.*
import grails.test.mixin.*

@TestFor(BiddingController)
@Mock(Bidding)
class BiddingControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/bidding/list" == response.redirectedUrl
    }

    /*void testList() {

        def model = controller.list()

        assert model.biddingInstanceList.size() == 0
        assert model.biddingInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.biddingInstance != null
    }

    void testSave() {
        controller.save()

        assert model.biddingInstance != null
        assert view == '/bidding/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/bidding/show/1'
        assert controller.flash.message != null
        assert Bidding.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/bidding/list'


        populateValidParams(params)
        def bidding = new Bidding(params)

        assert bidding.save() != null

        params.id = bidding.id

        def model = controller.show()

        assert model.biddingInstance == bidding
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/bidding/list'


        populateValidParams(params)
        def bidding = new Bidding(params)

        assert bidding.save() != null

        params.id = bidding.id

        def model = controller.edit()

        assert model.biddingInstance == bidding
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/bidding/list'

        response.reset()


        populateValidParams(params)
        def bidding = new Bidding(params)

        assert bidding.save() != null

        // test invalid parameters in update
        params.id = bidding.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/bidding/edit"
        assert model.biddingInstance != null

        bidding.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/bidding/show/$bidding.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        bidding.clearErrors()

        populateValidParams(params)
        params.id = bidding.id
        params.version = -1
        controller.update()

        assert view == "/bidding/edit"
        assert model.biddingInstance != null
        assert model.biddingInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/bidding/list'

        response.reset()

        populateValidParams(params)
        def bidding = new Bidding(params)

        assert bidding.save() != null
        assert Bidding.count() == 1

        params.id = bidding.id

        controller.delete()

        assert Bidding.count() == 0
        assert Bidding.get(bidding.id) == null
        assert response.redirectedUrl == '/bidding/list'
    }*/
}
