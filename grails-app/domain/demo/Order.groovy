package demo

class Order {

    String customer
    String email
    OrderStatus status = OrderStatus.NEW
    BigDecimal total
    Date dateCreated

    // ORDER is a reserved word in SQL, so the table needs an explicit name.
    static mapping = {
        table 'orders'
    }

    static constraints = {
        customer blank: false
        email email: true, blank: false
        total min: 0.0G
    }

    String toString() {
        "#${id} ${customer} (${status})"
    }
}
