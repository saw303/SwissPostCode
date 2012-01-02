package ch.silviowangler.postcode

import org.junit.After
import org.junit.Before
import org.junit.Test

class SwissPostCodeServiceTests {
    
    def SwissPostcodeService swissPostcodeService

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void findWallisellen() {
        verifyPostcode('8304', 'Wallisellen', 'Wallisellen', 'ZH')
    }

    @Test
    void findBruettisellen() {
        verifyPostcode('8306', 'Brüttisellen', 'Brüttisellen', 'ZH')
    }

    @Test
    void findBassersdorf() {
        verifyPostcode('8303', 'Bassersdorf', 'Bassersdorf', 'ZH')
    }

    private void verifyPostcode(final String postcode, final String city, final String cityShortName, final String canton) {
        def postcodes = swissPostcodeService.findPostcodes(postcode)

        assert 1 == postcodes.size()

        SwissPostcode wallisellen = postcodes[0]

        assert cityShortName == wallisellen.cityShortName
        assert city == wallisellen.city
        assert postcode == wallisellen.postcode
        assert canton == wallisellen.canton
    }
}
