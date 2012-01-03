package ch.silviowangler.postcode

import grails.test.mixin.TestFor
import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SwissPostcodeController)
class SwissPostcodeControllerTests {

    @Test
    void findPostcodeThatHasNoIdSpecifiedAsJson() {

        def strictSwissPostcodeServiceMock = mockFor(SwissPostcodeService)

        strictSwissPostcodeServiceMock.demand.findPostcodes(0) {  String postcode ->
            //throw new IllegalAccessException('This method is not expected to be called in this unit test')
        }

        controller.swissPostcodeService = strictSwissPostcodeServiceMock.createMock()

        request.method = 'GET'

        controller.findPostcodeJSON()

        strictSwissPostcodeServiceMock.verify()

        assert 'application/json;charset=UTF-8' == response.contentType
        assert '{"postcodes":[]}' == response.text
    }

    @Test
    void findWallisellenAsJson() {

        def strictSwissPostcodeServiceMock = mockFor(SwissPostcodeService)

        final expectedPostcode = '8304'

        strictSwissPostcodeServiceMock.demand.findPostcodes(1) {  String postcode ->
            assert postcode == expectedPostcode
            return [new SwissPostcode(postcode: expectedPostcode, city: 'Wallisellen', canton: 'ZH', cityShortName: 'Wallisellen')]
        }

        params.id = expectedPostcode

        controller.swissPostcodeService = strictSwissPostcodeServiceMock.createMock()

        request.method = 'GET'

        controller.findPostcodeJSON()

        strictSwissPostcodeServiceMock.verify()

        assert 'application/json;charset=UTF-8' == response.contentType
        assert '{"postcodes":[{"code":"8304","city":"Wallisellen","canton":"ZH","cityShortName":"Wallisellen"}]}' == response.text
    }

    @Test
    void findMultipleAsJson() {

        def strictSwissPostcodeServiceMock = mockFor(SwissPostcodeService)

        final expectedPostcode = '830'

        strictSwissPostcodeServiceMock.demand.findPostcodes(1) {  String postcode ->
            assert postcode == expectedPostcode
            return [new SwissPostcode(postcode: '8304', city: 'Wallisellen', canton: 'ZH', cityShortName: 'Wallisellen'),
                    new SwissPostcode(postcode: '8303', city: 'Bassersdorf', canton: 'ZH', cityShortName: 'Bassersdorf')]
        }

        params.id = expectedPostcode

        controller.swissPostcodeService = strictSwissPostcodeServiceMock.createMock()

        request.method = 'GET'

        controller.findPostcodeJSON()

        strictSwissPostcodeServiceMock.verify()

        assert 'application/json;charset=UTF-8' == response.contentType
        assert 2 == response.json.postcodes.size()
        assert '{"postcodes":[{"code":"8304","city":"Wallisellen","canton":"ZH","cityShortName":"Wallisellen"},{"code":"8303","city":"Bassersdorf","canton":"ZH","cityShortName":"Bassersdorf"}]}' == response.text
    }
}
