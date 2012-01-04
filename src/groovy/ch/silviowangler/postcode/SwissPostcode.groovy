package ch.silviowangler.postcode

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class SwissPostcode {
    String cityShortName
    String city
    String postcode
    String canton
}
