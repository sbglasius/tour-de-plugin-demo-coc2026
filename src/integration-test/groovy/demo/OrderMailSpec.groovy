package demo

import grails.gorm.transactions.Rollback
import grails.plugin.greenmail.GreenMail
import grails.testing.mixin.integration.Integration
import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

/**
 * What GreenMail is actually for.
 *
 * The plugin registers a `greenMail` bean that runs an SMTP server in-process,
 * so mail the application sends during a test is captured instead of delivered.
 * Nothing reaches a real inbox, and the test can assert on what was sent.
 */
@Integration
@Rollback
class OrderMailSpec extends Specification {

    @Autowired
    GreenMail greenMail

    OrderService orderService

    def setup() {
        // Each test starts from an empty inbox.
        greenMail.deleteAllMessages()
    }

    private Order newOrder(Map overrides = [:]) {
        new Order([customer: 'Ada Lovelace',
                   email   : 'ada@example.com',
                   status  : OrderStatus.SHIPPED,
                   total   : 249.00G] + overrides).save(flush: true, failOnError: true)
    }

    void 'the confirmation mail is captured rather than delivered'() {
        given: 'an order'
            Order order = newOrder()

        when: 'its confirmation is sent'
            orderService.sendConfirmation(order.id, Locale.ENGLISH)

        then: 'GreenMail caught exactly one message'
            greenMail.waitForIncomingEmail(5_000, 1)
            greenMail.messagesCount == 1

        and: 'addressed to the customer'
            MimeMessage mail = greenMail.latestMessage
            // `to` comes from the plugin's Groovy extension on MimeMessage.
            mail.to == 'ada@example.com'

        and: 'with the order and the customer in it'
            mail.subject == "Order #${order.id} is Shipped"
            mail.content.toString().contains('Ada Lovelace')
    }

    void 'the subject is translated, because @I18nEnum resolves the status'() {
        given:
            Order order = newOrder(email: 'grace@example.com')

        when: 'the confirmation is sent to a Danish customer'
            orderService.sendConfirmation(order.id, new Locale('da'))

        then:
            greenMail.waitForIncomingEmail(5_000, 1)
            greenMail.latestMessage.subject == "Order #${order.id} is Afsendt"
    }

    void 'nothing is sent for an order that does not exist'() {
        when:
            orderService.sendConfirmation(-1L, Locale.ENGLISH)

        then:
            greenMail.messagesCount == 0
    }
}
