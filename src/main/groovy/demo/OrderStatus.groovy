package demo

/**
 * A plain enum. Grails renders the constant names verbatim -- NEW, PENDING,
 * SHIPPED -- which is what the i18n-enums step fixes.
 */
enum OrderStatus {
    NEW,
    PENDING,
    SHIPPED,
    CANCELLED
}
