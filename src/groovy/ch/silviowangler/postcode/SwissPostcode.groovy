package ch.silviowangler.postcode

class SwissPostcode {
    String cityShortName
    String city
    String postcode
    String canton

    String toString() {"{$postcode} {$city} (${canton})"}
}
