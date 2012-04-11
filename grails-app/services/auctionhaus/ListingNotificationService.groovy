package auctionhaus

import grails.converters.deep.JSON;
import org.apache.activemq.ActiveMQConnectionFactory



class ListingNotificationService {
     def jmsService
    //def mailService
    //static exposes = ['xfire','jms']
    String listingEndedQueue = "queue.listingended";

    def listingEnded(Listing listing) {


        if (listing?.dateEnded <= new Date()) {
        try {
            println("Listing ended call for ${listing}")
            sendListingEndedJMS(listing)
            //sendEmail("ujjwal55@gmail.com","YOu are the winner",listing)
        } catch (e) {
            println "exception: ${e}"
        }
        }
    }

        def sendListingEndedJMS(Listing listing) {

            println("sendListingEndedJMS for ${listing}")

            def messageString = (listing as JSON)
            //print messageString.toString()


          jmsService.send(service:"queue",messageString.toString(),"standard",null)
        println "completed sendListingJMS for: " + listing.description
        listing.NotificationSent=true

        }

    def sendEmail(emailID, title, listing){
        sendMail{
            to "${emailID}"
            subject "${title}"
            body (view: "/listing/emailmessage",model:[listing:listing])
        }
    }



}
