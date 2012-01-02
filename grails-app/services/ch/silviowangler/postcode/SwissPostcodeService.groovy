package ch.silviowangler.postcode

import org.springframework.beans.factory.InitializingBean
import org.springframework.util.Assert

class SwissPostcodeService implements InitializingBean {

    static transactional = false

    private Map<String, List<SwissPostcode>> postcodeMap = [:]
    private List<SwissPostcode> postcodeList = []

    private void initializePostcodes() {

        log.info("Start parsing postcode source file")

        def reader = new InputStreamReader(getClass().getResourceAsStream('/postcodes.txt'))

        reader.eachLine { line ->

            log.trace("Reading line ${line}")

            def temp = line.split('\t')
            final currentPostcode = temp[2]
            if (!postcodeMap[currentPostcode]) {
                log.debug("Adding new postcode ${currentPostcode} and initialize empty list")
                postcodeMap[currentPostcode] = []
            }
            final postcode = new SwissPostcode(cityShortName: temp[4], city: temp[5], postcode: currentPostcode, canton: temp[6])
            log.debug("Adding ${postcode} to index ${currentPostcode}")
            postcodeMap[currentPostcode] << postcode
            postcodeList << postcode
        }

        log.info("Done parsing postcode source file. Processed ${postcodeList.size()} postcode records and registered ${postcodeMap.size()} Swiss postcodeMap")
    }

    @Override
    void afterPropertiesSet() {
        initializePostcodes()
    }

    List<SwissPostcode> findPostcodes(String postcode) {

        Assert.hasText(postcode, 'Param [postcode] must have text')

        postcode = postcode.trim()

        if(isExactHit(postcode)) {
            log.debug("Found postcode '${postcode}' on index")
            return [ postcodeMap[postcode][0]].asImmutable()
        }
        else {
            log.debug("Trying to find postcode ${postcode} on list")
            def candidates = postcodeList.findAll { it.postcode.startsWith(postcode)}
            log.debug("Found ${candidates.size()} for postcode '${postcode}'")
            return candidates.asImmutable()
        }
    }

    private boolean isExactHit(String postcode) {
        return postcodeMap.containsKey(postcode)
    }
}
