package com.aula.https.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/auth")
public class CookieController {

    private final Map<String, String> sessions = new ConcurrentHashMap<>();

    @PostMapping("/login")
    public String login(@RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            String token = UUID.randomUUID().toString();
            sessions.put(token, username);

            ResponseCookie cookie = ResponseCookie.from("SESSIONID", token)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return "Login realizado com sucesso. Cookie seguro definido.";
        }
        return "Credenciais inválidas.";
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        String sessionId = extractCookie(request, "SESSIONID");
        if (sessionId != null && sessions.containsKey(sessionId)) {
            return "Perfil do usuário: " + sessions.get(sessionId);
        }
        return "Acesso negado. Faça login primeiro.";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = extractCookie(request, "SESSIONID");
        if (sessionId != null) {
            sessions.remove(sessionId);
        }

        ResponseCookie cookie = ResponseCookie.from("SESSIONID", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ZERO)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return "Logout realizado.";
    }

    private String extractCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
