package org.apache.coyote.http11;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.HttpRequestFactory;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.ResponseBody;
import org.apache.coyote.http11.response.ResponseHeaders;
import org.apache.coyote.http11.support.HttpStatus;
import org.apache.coyote.http11.support.ResponseProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResponseProviderTest {

    @DisplayName("로그인이 실패하면 401.html로 리다이렉트한다.")
    @Test
    void createResponse_loginFail() throws Exception {
        //given
        final String string = String.join("\r\n",
                "POST /login?account=gugu&passord=가짜 HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");
        InputStream inputStream = new ByteArrayInputStream(string.getBytes());
        HttpRequest httpRequest = HttpRequestFactory.parse(inputStream);

        //when
        HttpResponse response = ResponseProvider.createResponse(httpRequest);
        ResponseHeaders responseHeader = response.getHeaders();
        ResponseBody responseBody = response.getBody();

        //then
        assertAll(
                () -> response.getResponseLine().contains(HttpStatus.FOUND.code),
                () -> responseHeader.getStringHeaders().contains("Location: 401.html")
        );
    }

    @DisplayName("로그인이 성공하면 index.html로 리다이렉트한다.")
    @Test
    void createResponse_loginSuccess() throws Exception {
        //given
        final String string = String.join("\r\n",
                "POST /login?account=gugu&passord=password HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");
        InputStream inputStream = new ByteArrayInputStream(string.getBytes());
        HttpRequest httpRequest = HttpRequestFactory.parse(inputStream);

        //when
        HttpResponse response = ResponseProvider.createResponse(httpRequest);
        ResponseHeaders responseHeader = response.getHeaders();
        ResponseBody responseBody = response.getBody();

        //then
        assertAll(
                () -> response.getResponseLine().contains(HttpStatus.FOUND.code),
                () -> responseHeader.getStringHeaders().contains("Location: index.html")
        );
    }
}
