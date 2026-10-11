package demo

import grails.plugins.i18nEnums.DefaultNameCase
import grails.plugins.i18nEnums.annotations.I18nEnum

/**
 * A plain enum. Grails renders the constant names verbatim -- NEW, PENDING,
 * SHIPPED -- which is what the i18n-enums step fixes.
 */
@I18nEnum(defaultNameCase = DefaultNameCase.CAPITALIZE)
enum OrderStatus {
    NEW,
    PENDING,
    SHIPPED,
    CANCELLED
}
