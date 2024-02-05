package ba.sum.fsre.tereni.controllers;

import ba.sum.fsre.tereni.models.User;
import ba.sum.fsre.tereni.models.UserDetails;
import ba.sum.fsre.tereni.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.*;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;
    @GetMapping("/home")
    public String showHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        String role = userDetails.getMainRole();
        model.addAttribute("role", role);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Korisnici");
        return "Facilities/index";
    }
    @GetMapping("/users")
    public String listUsers (Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users/index";
    }

    // U klasi UserController

    @GetMapping("/users/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/add";
    }

    @PostMapping("/users/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()){
            model.addAttribute("user", user);
            return "users/add";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passwordEncoded = encoder.encode(user.getLozinka());
            user.setLozinka(passwordEncoded);
            user.setPotvrdaLozinke(passwordEncoded);
            userRepository.save(user);
            return "redirect:/users";
        }
    }
    @GetMapping("/korisnik/edit")
    @PreAuthorize("hasAuthority('KORISNIK')")
    public String showEditUserFormForLogin( Model model) {
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Long userid = user.getId();
        User osoba = userRepository.findById(userid)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID korisnika: " + userid));
        model.addAttribute("user", osoba);
        return "users/editUser";
    }

    @PostMapping("/korisnik/edit/{userId}")
    @PreAuthorize("hasAuthority('Korisnik')")
    public String updateUserLogin(@PathVariable Long userId, @ModelAttribute User user, Model model) {
        // Provjerite postoji li korisnik s tim ID-om
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID korisnika: " + userId));
        existingUser.setIme(user.getIme());
        existingUser.setPrezime(user.getPrezime());
        existingUser.setEmail(user.getEmail());
        // Lozinka
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String lozinka = encoder.encode(user.getLozinka());
        existingUser.setLozinka(lozinka);
        existingUser.setPotvrdaLozinke(lozinka);
        existingUser.setRoles(user.getRoles());
        // Postavite ostala polja po potrebi
        userRepository.save(existingUser);
        return "redirect:/home";
    }

    @PostMapping("/users/delete/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable Long userId) {
        userRepository.deleteById(userId);
        return "redirect:/users";
    }

    // U klasi UserController

    @GetMapping("/users/edit/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showEditUserForm(@PathVariable Long userId, Model model) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID korisnika: " + userId));
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("/users/edit/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUser(@PathVariable Long userId, @ModelAttribute User user, Model model) {
        // Provjerite postoji li korisnik s tim ID-om
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID korisnika: " + userId));
        existingUser.setIme(user.getIme());
        existingUser.setPrezime(user.getPrezime());
        existingUser.setEmail(user.getEmail());
        // Lozinka
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String lozinka = encoder.encode(user.getLozinka());
        existingUser.setLozinka(lozinka);
        existingUser.setPotvrdaLozinke(lozinka);
        existingUser.setRoles(user.getRoles());
        // Postavite ostala polja po potrebi
        userRepository.save(existingUser);
        return "redirect:/users";
    }
}
