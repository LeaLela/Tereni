package ba.sum.fsre.tereni.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/korisnik")
@PreAuthorize("hasAuthority('KORISNIK')")
public class KorisnikController {

    @GetMapping
    public String teacherDashboard(Model model) {
        // Dodajte potrebne atribute u model
        return "korisnik/dashboard";
    }

    // Ostale metode specifične za korisnike
}
