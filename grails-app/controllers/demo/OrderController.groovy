package demo

import grails.plugin.scaffolding.annotation.Scaffold
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_USER')
@Scaffold(Order)
class OrderController {

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
