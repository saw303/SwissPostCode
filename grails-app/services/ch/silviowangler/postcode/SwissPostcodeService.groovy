package ch.silviowangler.postcode

import org.springframework.beans.factory.InitializingBean

class SwissPostcodeService implements InitializingBean {

    static transactional = false

    def postcodes = [:]

    private void initializePostcodes() {
        def reader = new InputStreamReader(getClass().getResourceAsStream('/postcodes.txt'))

        reader.eachLine { line ->
            def temp = line.split('\t')
            final currentPostcode = temp[2]
            if (!postcodes[currentPostcode]) {
                postcodes[currentPostcode] = []
            }
            postcodes[currentPostcode] << new SwissPostcode(cityShortName: temp[4], city: temp[5], postcode: currentPostcode, canton: temp[6])
        }
    }

    @Override
    void afterPropertiesSet() {
        initializePostcodes()
    }

    List<SwissPostcode> findPostcodes(String postcode) {

        if(postcodes[postcode]) return [ postcodes[postcode][0]]
        return []
    }

}
