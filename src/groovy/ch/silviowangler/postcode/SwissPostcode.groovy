package ch.silviowangler.postcode

class SwissPostcode {
    String cityShortName
    String city
    String postcode
    String canton

    String toString() {"{$postcode} {$city} (${canton})"}

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        SwissPostcode that = (SwissPostcode) o

        if (canton != that.canton) return false
        if (city != that.city) return false
        if (cityShortName != that.cityShortName) return false
        if (postcode != that.postcode) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = cityShortName.hashCode()
        result = 31 * result + city.hashCode()
        result = 31 * result + postcode.hashCode()
        result = 31 * result + canton.hashCode()
        return result
    }
}
