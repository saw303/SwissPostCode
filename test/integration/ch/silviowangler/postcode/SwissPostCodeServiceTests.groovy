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
    
    @Test
    void findAllPostcodesStartingBy830() {
        List<SwissPostcode> postcodes = swissPostcodeService.findPostcodes('830')
        
        assert 19 == postcodes.size()
        
        for(SwissPostcode postcode : postcodes) {
            assert postcode.canton == 'ZH'
            assert postcode.postcode.startsWith('830')
        }
    }

    @Test
    void findAllPostcodesStartingBy800WithTrailingWhitespaces() {
        List<SwissPostcode> postcodes = swissPostcodeService.findPostcodes('800      ')

        assert 12 == postcodes.size()

        for(SwissPostcode postcode : postcodes) {
            assert postcode.canton == 'ZH'
            assert postcode.postcode.startsWith('800')
            assert postcode.postcode.length() == 4
        }
    }

    @Test
    void findAllPostcodesStartingBy840WithLeadingAndTrailingWhitespaces() {
        List<SwissPostcode> postcodes = swissPostcodeService.findPostcodes('  840      ')

        assert 16 == postcodes.size()

        for(SwissPostcode postcode : postcodes) {
            assert postcode.canton == 'ZH'
            assert postcode.postcode.startsWith('840')
            assert postcode.postcode.length() == 4
        }
    }

    @Test(expected = IllegalArgumentException.class)
    void findAllPostcodesThrowsIllegalArgumentExceptionOnBlankInput() {
        swissPostcodeService.findPostcodes('')
    }

    @Test(expected = IllegalArgumentException.class)
    void findAllPostcodesThrowsIllegalArgumentExceptionOnNullInput() {
        swissPostcodeService.findPostcodes(null)
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
