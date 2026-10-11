package demo

import grails.plugin.scaffolding.RestfulServiceController
import grails.plugin.scaffolding.annotation.Scaffold
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_USER')
@Scaffold(RestfulServiceController<Order>)
class OrderController {

    OrderService orderService

    // Sends the confirmation mail for an order. In development GreenMail
    // catches it; see /greenmail.
    def confirm(Long id) {
        def order = orderService.sendConfirmation(id, request.locale)
        flash.message = "Confirmation sent to ${order.email}"

        redirect action: 'index'
    }

    // Deleting an order takes more than being logged in. ROLE_ADMIN implies
    // ROLE_USER through the role hierarchy, so admins need only the one role.
    @Secured('ROLE_ADMIN')
    def delete(Long id) {
        Order.withTransaction {
            Order.get(id)?.delete(flush: true)
        }
        redirect action: 'index'
    }

}
