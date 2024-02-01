package ba.sum.fsre.tereni.controllers;

import ba.sum.fsre.tereni.repositories.FacilitiesRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ba.sum.fsre.tereni.repositories.ReservationRepository;
import ba.sum.fsre.tereni.models.User;
import ba.sum.fsre.tereni.models.Reservation;
import ba.sum.fsre.tereni.services.UserDetailsService;
import ba.sum.fsre.tereni.models.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ba.sum.fsre.tereni.repositories.UserRepository;

@Controller
public class ReservationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ReservationRepository reservationRepo;

    @Autowired
    UserDetailsService UserDetailsService;

    @Autowired
    FacilitiesRepository facilitiesRepo;

    @GetMapping("/reservation")
    public String listReservation(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Reservation");

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
        boolean isUser = userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("KORISNIK"));

        if (isAdmin) {
            List<Reservation> listReservation = reservationRepo.findAll();
            model.addAttribute("listReservation", listReservation);
            return "Reservation/index";
        } else if (isUser) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            Long userId = user.getId();
            List<Reservation> listReservation = reservationRepo.findByUserId(userId);
            model.addAttribute("listReservation", listReservation);
            return "Reservation/index";
        } else {
            // Ako korisnik nema ni ROLE_ADMIN ni ROLE_USER ulogu
            // Možete ovdje upravljati defaultnim ponašanjem
            return "redirect:/";
        }
    }

    @GetMapping("/reservation/add")
    public String showCreateForm(Model model) {
        List<Facilities> facilities = facilitiesRepo.findAll();
        model.addAttribute("facilitiesList", facilities);
        model.addAttribute("reservation", new Reservation());
        return "Reservation/create";
    }
    @PostMapping("/reservation/add")
    public String createReservation(@ModelAttribute("reservation") @Valid Reservation reservation, BindingResult result, Model model, @RequestParam("facilitiesId") Long facilitiesId) {
        if (result.hasErrors()) {
            return "Reservation/create"; // Ovdje vratite formu za kreiranje sa greškama validacije
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userRepository.findByEmail(currentUserName);
        reservation.setUser(user);

        Facilities facilities = facilitiesRepo.findById(facilitiesId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid facilities Id:" + facilitiesId));
        reservation.setFacilities(facilities);

        reservationRepo.save(reservation);
        return "redirect:/reservation";
    }


    @GetMapping("/reservation/add/{id}")
    public String showCreate(@PathVariable("id") long id,Model model) {
        List<Facilities> facilitiesList = facilitiesRepo.findAll();
        model.addAttribute("facilitiesList", facilitiesList);
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("facilitiid", id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("activeLink", "Reservation");
        model.addAttribute("userDetails", userDetails);
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Long userid = user.getId();


        model.addAttribute("username", userid);
        return "Reservation/create";
    }

    @GetMapping("/reservation/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        Reservation reservation = reservationRepo .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
        reservationRepo.delete(reservation);
        return "redirect:/reservation";
    }
}
