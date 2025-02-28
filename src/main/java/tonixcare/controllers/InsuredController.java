package tonixcare.controllers;
//importy

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tonixcare.model.dto.InsuredDTO;
import tonixcare.model.services.InsuredService;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/insureds")
public class InsuredController {

    private final InsuredService insuredService;

    @Autowired
    public InsuredController(InsuredService insuredService) {
        this.insuredService = insuredService;
    }

    //  seznam pojištěnců
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public String listAllInsureds(Model model) {
        List<InsuredDTO> insuredList = insuredService.getAll();
        model.addAttribute("insureds", insuredList);
        return "pages/insured/insuredList"; // HTML šablona pro zobrazení seznamu
    }

    // DETAIL pojištěnce
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/detail/{insuredId}")
    public String showInsuredDetail(@PathVariable long insuredId, Model model, RedirectAttributes redirectAttributes) {
        try {
            InsuredDTO insured = insuredService.getById(insuredId);
            model.addAttribute("insured", insured);
            return "pages/insured/insuredDetail"; // HTML šablona pro detail
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Pojištěnec nebyl nalezen.");
            return "redirect:/insureds/list";
        }
    }

    // vytvoření nového pojištěnce
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String createInsuredForm(Model model) {
        model.addAttribute("insured", new InsuredDTO()); // Prázdný DTO objekt pro formulář
        return "pages/insured/createInsured"; // HTML šablona pro formulář
    }

    // zpracování, vytvoření pojištěnce
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createInsured(@Valid @ModelAttribute InsuredDTO insuredDTO,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "pages/insured/createInsured"; // Pokud jsou chyby, zůstane na stránce
        }

        try {
            insuredService.create(insuredDTO); // Volání servisní metody pro uložení pojištěnce
            redirectAttributes.addFlashAttribute("success", "Pojištěnec byl úspěšně přidán.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Chyba při přidávání pojištěnce.");
        }

        return "redirect:/insureds/list";
    }

    // zobrazit formulář pro úpravu
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{insuredId}")
    public String editInsured(@PathVariable long insuredId, Model model, RedirectAttributes redirectAttributes) {
        try {
            InsuredDTO insured = insuredService.getById(insuredId);
            model.addAttribute("insured", insured);
            return "pages/insured/editInsured"; // HTML šablona pro editaci
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Pojištěnec nebyl nalezen.");
            return "redirect:/insureds/list";
        }
    }

    // uprava pojištěnce
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{insuredId}")
    public String updateInsured(@PathVariable long insuredId,
                                @Valid @ModelAttribute InsuredDTO insuredDTO,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "pages/insured/editInsured"; // Vrací formulář s chybami
        }

        try {
            insuredDTO.setInsuredId(insuredId);
            insuredService.edit(insuredDTO);
            redirectAttributes.addFlashAttribute("success", "Pojištěnec byl úspěšně upraven.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Chyba při úpravě pojištěnce.");
        }

        return "redirect:/insureds/list";
    }

    // smzani pojištěnce
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{insuredId}")
    public String deleteInsured(@PathVariable long insuredId, RedirectAttributes redirectAttributes) {
        try {
            insuredService.remove(insuredId);
            redirectAttributes.addFlashAttribute("success", "Pojištěnec byl úspěšně smazán.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Chyba při mazání pojištěnce.");
        }
        return "redirect:/insureds/list";
    }
}
