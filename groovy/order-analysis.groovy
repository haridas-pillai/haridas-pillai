
/*
 * SAP Commerce Cloud - Order Analysis Utility
 *
 * Purpose:
 * Retrieve basic order information using an order code.
 *
 * Execution:
 * Run through HAC > Console > Scripting > Groovy.
 *
 * This script is read-only.
 */

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery

// Configure the order code
def orderCode = "YOUR_ORDER_CODE"

try {
    def flexibleSearchService = spring.getBean("flexibleSearchService")

    def query = new FlexibleSearchQuery(
        "SELECT {pk} FROM {Order} WHERE {code} = ?orderCode"
    )

    query.addQueryParameter("orderCode", orderCode)

    def results = flexibleSearchService.search(query).result

    if (results.isEmpty()) {
        println "No order found for code: ${orderCode}"
    } else {
        def order = results[0]

        println "========== ORDER DETAILS =========="
        println "Order Code    : ${order.code}"
        println "Status        : ${order.status}"
        println "Creation Date : ${order.creationtime}"
        println "Total Price   : ${order.totalPrice}"
        println "Currency      : ${order.currency?.isocode}"
        println "Site          : ${order.site?.uid}"
        println "==================================="
    }

} catch (Exception e) {
    println "ERROR: Unable to retrieve order details"
    println "Order Code: ${orderCode}"
    println "Exception : ${e.class.name}"
    println "Message   : ${e.message}"

    e.printStackTrace()
}
