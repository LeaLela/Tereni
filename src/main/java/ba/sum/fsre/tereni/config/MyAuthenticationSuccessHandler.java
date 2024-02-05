package ba.sum.fsre.tereni.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Preuzmimanje uloge korisnika.
        var authorities = authentication.getAuthorities();

        // preusmjeravanje s obzirom na to koja je uloga dodijeljena
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            response.sendRedirect("/users");
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("KORISNIK"))) {
            response.sendRedirect("/Reservation");
        } else {
            response.sendRedirect("/login");
        }
    }
}