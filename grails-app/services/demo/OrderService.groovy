package demo


import grails.plugin.scaffolding.annotation.Scaffold
import grails.plugins.mail.MailService
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

@Scaffold(Order)
class OrderService {

    MessageSource messageSource

    /**
     * In development this never leaves the machine: GreenMail runs an SMTP
     * server in-process and captures it. Browse what was sent at /greenmail.
     */
    Order sendConfirmation(Long orderId, Locale locale) {
        Order order = Order.get(orderId)
        if(!order) {
            return null
        }
        // @I18nEnum makes OrderStatus a MessageSourceResolvable, so it can be
        // handed straight to messageSource -- this works outside a GSP too.
        String status = messageSource.getMessage(order.status, locale)

        sendMail {
            to order.email
            from 'orders@tourdeplugin.example'
            subject "Order #${order.id} is ${status}"
            text """\
                Hello ${order.customer},

                Your order #${order.id} is now ${status}.
                Total: ${order.total}

                -- Tour De Plugin
                """.stripIndent()
        }
        
        return order
    }

}
