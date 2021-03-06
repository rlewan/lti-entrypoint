package com.boclips.lti.v1p1.presentation

import com.boclips.lti.v1p1.configuration.properties.LtiProperties
import com.boclips.lti.v1p1.domain.service.IsLaunchRequestValid
import org.imsglobal.aspect.Lti
import org.imsglobal.lti.launch.LtiVerificationResult
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/lti/v1p1")
class LtiOnePointOneController(
    val isLaunchRequestValid: IsLaunchRequestValid,
    val ltiProperties: LtiProperties
) {
    @Lti
    @PostMapping("", "/")
    fun handleLtiLaunchRequest(request: HttpServletRequest, result: LtiVerificationResult): ResponseEntity<Unit> {
        val responseHeaders = HttpHeaders()
        if (isLaunchRequestValid(result)) {
            // TODO Establish a user session
            responseHeaders.location = URI(ltiProperties.landingPage)
        } else {
            responseHeaders.location = URI(ltiProperties.errorPage)
        }
        return ResponseEntity(responseHeaders, HttpStatus.SEE_OTHER)
    }
}
