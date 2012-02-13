package auctionhaus



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Customer)
class CustomerTests {

    def customer
    final static validMaxPasswordSize = 8
    final static validMinPasswordSize = 6
    protected void setUp()
    {
        super.setUp()
    }

    //test blank Customer email
    void testEmailIsRequired()
    {
        //C-1: Customers have email address, password and created date fields (unit test)
        customer = new Customer(password:"abcdefd")
        customer.validate()
        assert 1 == customer.errors.fieldErrorCount
        assert "nullable"  == customer.errors["email"].code
    }


    void testPasswordIsRequired()
    {
        //C-1: Customers have email address, password and created date fields (unit test)
        customer = new Customer(email:"abc@aol.net")
        customer.validate()
        assert 1 == customer.errors.fieldErrorCount
        assert "nullable"  == customer.errors["password"].code
    }

    void testDateCreatedIsRequired()
    {
        //C-1: Customers have email address, password and created date fields (unit test)
        customer = new Customer(email:"abc@aol.net", password:"abcdef")
        customer.validate()
        //dateCreated is generated by grails
        assert 0 == customer.errors.fieldErrorCount
    }

    void testPasswordIsBelowMax()
    {
        //C-4: Password must be between 6-8 characters
        customer = new Customer(email:"abc@aol.net", password:"abcdef1254")
        customer.validate()
        assert 1 == customer.errors.fieldErrorCount
        assert customer.password.size() >= validMaxPasswordSize
    }

    void testPasswordIsAboveMin()
    {
        //C-4: Password must be between 6-8 characters
        customer = new Customer(email:"abc@aol.net", password:"ab")
        customer.validate()
        assert 1 == customer.errors.fieldErrorCount
        assert customer.password.size() <= validMinPasswordSize
    }


    void testPasswordIsBetweenRightSize()
    {
        //C-4: Password must be between 6-8 characters
        customer = new Customer(email:"abc@aol.net", password:"abcdef")
        customer.validate()
        assert 0 == customer.errors.fieldErrorCount
        assert customer.password.size() >= validMinPasswordSize
        assert customer.password.size()  <= validMaxPasswordSize
    }

    void testEmailIsValid()
    {
        //C-4: Password must be between 6-8 characters
        customer = new Customer(email:"abc", password:"abcdef")
        customer.validate()
        assert 1 == customer.errors.fieldErrorCount

       customer = new Customer(email:"abc@j@", password:"abcdef")
       customer.validate()
       assert 1 == customer.errors.fieldErrorCount

        customer = new Customer(email:"abc@gmail..com", password:"abcdef")
        customer.validate()
        assert 1 == customer.errors.fieldErrorCount

        //clean email
        customer = new Customer(email:"abc@gmail.com", password:"abcdef")
        customer.validate()
        assert 0 == customer.errors.fieldErrorCount
    }
}
