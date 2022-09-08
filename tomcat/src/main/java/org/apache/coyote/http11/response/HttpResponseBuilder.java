package org.apache.coyote.http11.response;

import static org.apache.coyote.http11.HeaderField.CONTENT_LENGTH;
import static org.apache.coyote.http11.HeaderField.CONTENT_TYPE;
import static org.apache.coyote.http11.HeaderField.LOCATION;

import java.io.IOException;
import org.apache.coyote.http11.HttpStatus;
import org.apache.coyote.http11.request.HttpRequest;

public class HttpResponseBuilder {

    public static HttpResponse ok(HttpRequest request) throws IOException {
        final String requestUri = request.getRequestUri();
        final ResponseBody body = ResponseBody.from(requestUri);
        final ResponseHeaders headers = ResponseHeaders.create()
                .addHeader(CONTENT_TYPE, ContentType.find(requestUri) + ";charset=utf-8 ")
                .addHeader(CONTENT_LENGTH, body.getBody());
        return HttpResponse.create(HttpStatus.OK, headers, body);
    }

    public static HttpResponse found(String redirectUrl) {
        final ResponseBody body = new ResponseBody();
        final ResponseHeaders headers = ResponseHeaders.create()
                .addHeader(LOCATION, redirectUrl);
        return HttpResponse.create(HttpStatus.FOUND, headers, body);
    }

    public static HttpResponse found(String redirectUrl, ResponseHeaders headers) {
        final ResponseBody body = new ResponseBody();
        headers.addHeader(LOCATION, redirectUrl);
        return HttpResponse.create(HttpStatus.FOUND, headers, body);
    }
}