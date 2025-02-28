package tonixcare.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tonixcare.model.dto.UserDTO;
import tonixcare.model.services.UserService;
import tonixcare.entities.UserEntity;
import tonixcare.entities.InsuranceEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;


    // Přihlašovací stránka
    @GetMapping("login")
    public String renderLogin() {
        return "/pages/account/login.html";
    }


    // Registrační stránka (GET)
    @GetMapping("register")
    public String renderRegister(@ModelAttribute UserDTO userDTO) {
        return "/pages/account/register";
    }



    // Registrace uživatele (POST)
    @PostMapping("register")
    public String register(
            @Valid @ModelAttribute UserDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        // Validace formuláře
        if (result.hasErrors()) {
            return renderRegister(userDTO);
        }

        // Validace hesel
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            result.rejectValue("password", "error", "Hesla se nerovnají.");
            result.rejectValue("confirmPassword", "error", "Hesla se nerovnají.");
            return "/pages/account/register";
        }

        // Pokus o vytvoření uživatele
        try {
            userService.create(userDTO, false);
        } catch (Exception e) {
            result.rejectValue("email", "error", "Email je již používán.");
            result.rejectValue("phoneNumber", "error", "Telefonní číslo je již používáno.");
            return "/pages/account/register"; // Vrátí zpět na registrační formulář
        }

        // Úspěšná registrace
        redirectAttributes.addFlashAttribute("success", "Uživatel zaregistrován.");
        return "redirect:/account/login";
    }



    // Seznam pojištěnců (pouze pro ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("user-list")
    public String renderList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/pages/account/userList"; // Seznam pojištěnců
    }



    // Metoda pro mazání pojištěnce (pouze pro ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable long userId, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(userId);  // Volání metody pro smazání uživatele
            redirectAttributes.addFlashAttribute("success", "Uživatel byl úspěšně smazán.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Došlo k chybě při mazání uživatele.");
        }
        return "redirect:/account/user-list";  // Přesměrování na seznam uživatelů
    }



    // Metoda pro úpravu pojištěnce (pouze pro ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{userId}")
    public String editUser(@PathVariable Long userId, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Ověření, zda uživatel existuje
            UserEntity user = userService.getUserById(userId);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Uživatel nebyl nalezen.");
                return "redirect:/account/user-list"; // Přesměrování, pokud uživatel neexistuje
            }

            // Přenos dat do DTO pro editaci
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setEmail(user.getEmail());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setPhoneNumber(user.getPhoneNumber());

            model.addAttribute("user", userDTO); // Přidání do modelu pro šablonu

            return "pages/account/editUser"; // Název šablony pro úpravy
        } catch (Exception e) {
            // Logování chyby a přesměrování na seznam uživatelů
            redirectAttributes.addFlashAttribute("error", "Chyba při načítání uživatele.");
            return "redirect:/account/user-list"; // Přesměrování při chybě
        }
    }



//Post metoda pro editaci uživatele
    @PostMapping("/edit/{userId}")
    public String updateUser(@PathVariable Long userId,
                             @Valid @ModelAttribute UserDTO userDTO,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "pages/account/editUser";
        }

        try {
            userDTO.setUserId(userId);

            // Volání metody pro aktualizaci uživatele
            userService.updateUser(userId, userDTO);

            // Úspěšná aktualizace
            redirectAttributes.addFlashAttribute("success", "Uživatel byl úspěšně upraven.");
        } catch (Exception e) {
            // Logování chyby při aktualizaci a přesměrování
            redirectAttributes.addFlashAttribute("error", "Chyba při úpravě uživatele.");
        }

        return "redirect:/account/user-list"; // Po úspěšné úpravy přesměrování na seznam uživatelů
    }





    // Stránka "Moje pojištění" pro přihlášeného uživatele
    @PreAuthorize("isAuthenticated()")
    @GetMapping("myInsurances")
    public String renderMyInsurances(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername(); // Získání emailu přihlášeného uživatele
        List<InsuranceEntity> insurances = userService.getInsurancesByEmail(email); // Získání pojištění pro tento email
        model.addAttribute("insurances", insurances);

        // Získání údajů o uživateli (pro zobrazení na stránce)
        UserEntity user = userService.findByEmail(email);
        model.addAttribute("user", user);
        model.addAttribute("insurance", new InsuranceEntity());

        return "/pages/account/myInsurances";  // Zobrazení pojištění na stránce
    }
}
