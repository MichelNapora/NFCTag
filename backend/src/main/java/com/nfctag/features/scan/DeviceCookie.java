package com.nfctag.features.scan;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;

/**
 * Le cookie qui permet de reconnaître le téléphone d'un technicien.
 * Il survit plus longtemps que le stockage du navigateur, que Safari purge
 * après 7 jours d'inactivité.
 */
@Component
public class DeviceCookie {

    private static final String NAME = "nfctag_device";

    @Value("${nfctag.device-cookie-days}") private long days;
    @Value("${nfctag.device-cookie-secure}") private boolean secure;

    /** Le jeton mémorisé par le téléphone, ou null si personne ne l'a posé. */
    public UUID read(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null) { return null; }

        return Arrays.stream(cookies)
                .filter(c -> NAME.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .map(this::parse)
                .orElse(null);
    }

    /**
     * Repose le cookie : sa durée de vie repart de zéro à chaque scan,
     * donc un téléphone qui revient régulièrement n'est jamais oublié.
     */
    public void write(HttpServletResponse response, UUID deviceToken){
        ResponseCookie cookie = ResponseCookie.from(NAME, deviceToken.toString())
                .httpOnly(true)
                .secure(this.secure)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofDays(this.days))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    /** Un cookie trafiqué ne doit pas faire échouer le scan : on l'ignore. */
    private UUID parse(String value){
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}