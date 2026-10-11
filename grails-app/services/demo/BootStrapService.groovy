package demo

import grails.gorm.transactions.Transactional

@Transactional
class BootStrapService {

    void createOrderData() {
        if (!Order.count()) {
            new Order(customer: 'Ada Lovelace', email: 'ada@example.com',
                    status: OrderStatus.NEW, total: 249.00G).save(failOnError: true)
            new Order(customer: 'Grace Hopper', email: 'grace@example.com',
                    status: OrderStatus.PENDING, total: 1099.50G).save(failOnError: true)
            new Order(customer: 'Alan Turing', email: 'alan@example.com',
                    status: OrderStatus.SHIPPED, total: 42.00G).save(failOnError: true)
            new Order(customer: 'Barbara Liskov', email: 'barbara@example.com',
                    status: OrderStatus.CANCELLED, total: 15.75G).save(failOnError: true)
        }
    }

    void createUserAndRoles() {
        if (!Role.count()) {
            def userRole = new Role(authority: 'ROLE_USER').save(failOnError: true)
            def adminRole = new Role(authority: 'ROLE_ADMIN').save(failOnError: true)

            def soeren = new User(username: 'sbglasius', password: 'password').save(failOnError: true)
            def guest = new User(username: 'guest', password: 'password').save(failOnError: true)

            UserRole.create(soeren, adminRole)
            UserRole.create(guest, userRole)
        }
    }
    
}
