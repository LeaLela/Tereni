package ba.sum.fsre.tereni.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ba.sum.fsre.tereni.repositories.ReservationRepository;
import ba.sum.fsre.tereni.models.User;
import ba.sum.fsre.tereni.models.Reservation;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ba.sum.fsre.tereni.repositories.UserRepository;
@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public ReservationController(ReservationRepository reservationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/form")
    public String showReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation-form";
    }

    @PostMapping("/make")
    public String makeReservation(@ModelAttribute Reservation reservation) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByEmail(currentPrincipalName);

        reservation.setStartTime(LocalDateTime.now());
        reservation.setUser(user);

        reservationRepository.save(reservation);

        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByEmail(currentPrincipalName);
        List<Reservation> reservations = reservationRepository.findByUser(user);
        model.addAttribute("reservations", reservations);

        return "dashboard";
    }
}
