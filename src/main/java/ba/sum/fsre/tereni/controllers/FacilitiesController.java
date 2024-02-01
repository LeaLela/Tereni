package ba.sum.fsre.tereni.controllers;


import ba.sum.fsre.tereni.models.Facilities;
import ba.sum.fsre.tereni.models.Reservation;
import ba.sum.fsre.tereni.models.User;
import ba.sum.fsre.tereni.models.UserDetails;
import ba.sum.fsre.tereni.repositories.FacilitiesRepository;
import ba.sum.fsre.tereni.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FacilitiesController {
    @Autowired
    FacilitiesRepository facilitiesRepo;
    @Autowired
    UserRepository userRepo;

    @GetMapping("/facilities")
    public String listFacilities(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = authentication.getName();
        User user = userRepo.findByEmail(email);
        String role = userDetails.getMainRole();
        model.addAttribute("role", role);

        model.addAttribute("userDetails", userDetails);
        List<Facilities> listFacilities = facilitiesRepo.findAll();
        model.addAttribute("listFacilities", listFacilities);
        model.addAttribute("activeLink", "Facilities");
        return "Facilities/index";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/facilities/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();


        Facilities facilities = facilitiesRepo .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
        facilitiesRepo.delete(facilities);
        return "redirect:/reservation";
    }

}
