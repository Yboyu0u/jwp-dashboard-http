package nextstep.jwp.controller;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public enum ControllerMapper {
    LOGIN(url -> url.contains("/login"), controller -> new LoginController()),
    REGISTER(url -> url.contains("/register"), controller -> new RegisterController()),
    HOME(url -> url.contains("/"), controller -> new HomePageController());

    private final Predicate<String> findUrl;
    private final Function<String, AbstractController> findController;

    ControllerMapper(Predicate<String> findUrl, Function<String, AbstractController> findController) {
        this.findUrl = findUrl;
        this.findController = findController;
    }

    public static AbstractController findController(String requestUri) {
        return findUrl(requestUri)
                .findController.apply(requestUri);
    }

    private static ControllerMapper findUrl(String requestUri) {
        return Arrays.stream(values())
                .filter(controllerMapper -> controllerMapper.findUrl.test(requestUri))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}