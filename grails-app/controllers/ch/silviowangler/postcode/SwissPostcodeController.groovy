package ch.silviowangler.postcode

class SwissPostcodeController {

    static allowedMethods = [findPostcode: 'GET']

    def swissPostcodeService

    def findPostcodeJSON() {
        def results = params.id ? swissPostcodeService.findPostcodes(params.id) : []

        render(contentType: 'text/json') {
            postcodes = array {
                for (pc in results) {
                    postcode code: pc.postcode, city: pc.city, canton: pc.canton, cityShortName: pc.cityShortName
                }
            }
        }
    }
}
