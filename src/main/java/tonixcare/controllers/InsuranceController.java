package tonixcare.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tonixcare.entities.InsuranceEntity;
import tonixcare.entities.UserEntity;
import tonixcare.model.dto.InsuranceDTO;
import tonixcare.model.dto.InsuredDTO;
import tonixcare.model.services.InsuranceService;
import tonixcare.model.services.InsuredService;
import tonixcare.model.services.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/insurances")
public class InsuranceController {

    @Autowired
    private UserService userService;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private InsuredService insuredService;

    // Přidání uživatele do modelu pro všechny metody
    @ModelAttribute("user")
    public UserEntity getAuthenticatedUser(Authentication authentication) {
        String email = authentication.getName();
        return userService.findByEmail(email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create/{insuredId}")
    public String createInsuranceForm(@PathVariable long insuredId, Model model) {
        // Získání pojištěnce podle ID
        InsuredDTO insured = insuredService.getById(insuredId);
        System.out.println("Pojištěnec ID: " + insuredId);
        if (insured == null) {
            System.out.println("Pojištěnec s ID " + insuredId + " nebyl nalezen.");
            return "redirect:/error"; // Pokud pojištěnec neexistuje, přesměrujeme na chybu
        }

        System.out.println("Pojištěnec s ID " + insuredId + " nalezen.");

        // Seznam typů pojištění
        String[] insuranceTypes = {"Životní pojištění", "Úrazové pojištění", "Cestovní pojištění", "Pojištění vozidel"};

        // Předání pojištěnce a seznamu typů pojištění do modelu
        model.addAttribute("insured", insured); // Předání pojištěnce
        model.addAttribute("insurance", new InsuranceEntity()); // Nový objekt pojištění
        model.addAttribute("insuranceTypes", insuranceTypes);  // Seznam typů pojištění

        return "pages/insurance/createInsurance";  // Návrat na formulář pro vytvoření pojištění
    }


    @PostMapping("/create/{insuredId}")
    public String createInsurance(@PathVariable long insuredId, @ModelAttribute InsuranceEntity insuranceEntity, Model model, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Received insuredId from path: " + insuredId);
            System.out.println("Received insuredId in insuranceEntity: " + insuranceEntity.getInsured().getInsuredId());
            System.out.println("Received insurance type: " + insuranceEntity.getInsuranceType());
            System.out.println("Received insured amount: " + insuranceEntity.getInsuredAmount());
            System.out.println("Received insured address: " + insuranceEntity.getInsuredAddress());

            // zpracovaní
            InsuredDTO insured = insuredService.getById(insuredId);
            if (insured == null) {
                model.addAttribute("error", "Pojištěnec nebyl nalezen.");
                return "redirect:/insurances/create/" + insuredId;
            }

            insuranceEntity.setInsured(insured);
            insuranceService.createInsurance(insuranceEntity); // Uložení pojištění

            // Přidání flash zprávy o úspěšném vytvoření pojištění
            redirectAttributes.addFlashAttribute("success", "Pojištění bylo úspěšně vytvořeno.");
            return "redirect:/insureds/detail/" + insuredId;  // Přesměrování na detail pojištěnce
        } catch (Exception e) {
            model.addAttribute("error", "Došlo k chybě při vytváření pojištění.");
            return "redirect:/insurances/create/" + insuredId;
        }
    }


    // Seznam pojištění pro admina
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String listAllUsers(Model model, Authentication authentication) {
        String email = authentication.getName();
        UserEntity user = userService.findByEmail(email);

        if (user.isAdmin()) {
            List<UserEntity> users = userService.getAllUsers();  // Získání všech uživatelů
            model.addAttribute("users", users);  // Předání seznamu uživatelů do modelu
            return "pages/insurance/insuranceListAdmin";  // Zobrazí šablonu pro administrátora
        } else {
            return "redirect:/insurances/userList";  // Přesměrování pro běžného uživatele
        }

    }

    // Smazání pojištění
    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{insuranceId}")
    public String deleteInsurance(@PathVariable long insuranceId, RedirectAttributes redirectAttributes) {
        Optional<InsuranceDTO> insuranceOptional = insuranceService.getInsuranceById(insuranceId);
        if (insuranceOptional.isPresent()) {
            insuranceService.deleteInsurance(insuranceId);
            redirectAttributes.addFlashAttribute("success", "Pojištění bylo úspěšně smazáno.");
        }

        // Získání pojištěnce a jeho pojištění po smazání
        InsuranceDTO insurance = insuranceOptional.get();
        InsuredDTO insured = insuredService.getById(insurance.getInsured().getInsuredId());
        if (insured != null) {
            redirectAttributes.addFlashAttribute("insured", insured); // Předání pojištěnce zpět do modelu
        }

        return "redirect:/insureds/detail/" + insurance.getInsured().getInsuredId();
    }


    // Editace pojištění
    @Secured("ROLE_ADMIN")  // Umožňuje pouze adminům přístup
    @GetMapping("/edit/{insuranceId}")
    public String renderEditInsurance(@PathVariable Long insuranceId, Model model) {
        Optional<InsuranceDTO> fetchedInsuranceOptional = insuranceService.getInsuranceById(insuranceId);
        if (fetchedInsuranceOptional.isEmpty()) {
            return "redirect:/insurances/list"; // Pokud pojištění neexistuje, přesměruj
        }

        InsuranceDTO fetchedInsurance = fetchedInsuranceOptional.get();

        // Seznam typů pojištění
        String[] insuranceTypes = {"Životní pojištění", "Úrazové pojištění", "Cestovní pojištění", "Pojištění vozidel"};

        // Přidání pojištění do modelu pro zobrazení v šabloně
        model.addAttribute("insurance", fetchedInsurance);
        model.addAttribute("insuranceTypes", insuranceTypes);  // Předání seznamu typů pojištění do modelu

        return "pages/insurance/editInsurance"; // Návrat na šablonu pro editaci pojištění
    }


    @Secured("ROLE_ADMIN")  // Umožňuje pouze adminům přístup
    @PostMapping("/edit/{insuranceId}")
    public String updateInsurance(@PathVariable Long insuranceId, @ModelAttribute InsuranceDTO insuranceDTO, RedirectAttributes redirectAttributes) {
        Optional<InsuranceDTO> fetchedInsuranceOptional = insuranceService.getInsuranceById(insuranceId);
        if (fetchedInsuranceOptional.isEmpty()) {
            return "redirect:/insurances/list"; // Pokud pojištění neexistuje, přesměruj
        }

        InsuranceDTO fetchedInsurance = fetchedInsuranceOptional.get();

        // Aktualizace pojištění
        insuranceService.updateInsurance(insuranceId, insuranceDTO);

        // Přidání flash zprávy pro úspěšnou aktualizaci
        redirectAttributes.addFlashAttribute("success", "Pojištění bylo úspěšně aktualizováno.");

        // Přesměrování na detail pojištěnce podle jeho ID
        return "redirect:/insureds/detail/" + fetchedInsurance.getInsured().getInsuredId(); // Předání ID pojištěnce
    }


    // Detail pojištění
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/insuranceDetail/{insuranceId}")
    public String showInsuranceDetail(@PathVariable long insuranceId, Model model, RedirectAttributes redirectAttributes) {
        // Načtení pojištění nebo vyhození výjimky
        InsuranceDTO fetchedInsurance = insuranceService.getInsuranceById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Pojištění nenalezeno"));

        // Přidání flash zprávy
        redirectAttributes.addFlashAttribute("success", "Pojištění úspěšně načteno.");

        model.addAttribute("insurance", fetchedInsurance);
        return "pages/insurance/insuranceDetail";
    }
}