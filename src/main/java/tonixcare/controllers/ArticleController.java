package tonixcare.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tonixcare.model.dto.ArticleDTO;
import tonixcare.model.ArticleMapper;
import tonixcare.model.exceptions.ArticleNotFoundException;
import tonixcare.model.services.ArticleService;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping
    public String renderIndex(Model model) {
        List<ArticleDTO> articles = articleService.getAll();
        model.addAttribute("articles", articles);

        return "pages/articles/index";
    }


    //METODA PRO ZOBRAZENI DETAILU CLANKU
    @GetMapping("{articleId}")
    public String renderDetail(
            @PathVariable long articleId,
            Model model
    ) {
        ArticleDTO article = articleService.getById(articleId);
        model.addAttribute("article", article);

        return "pages/articles/detail";
    }

    //metoda pro zobrazeni formulare pro vytvoreni clanku - pouze pro prihlasene uzivatele
    @PreAuthorize("isAuthenticated()")
    @GetMapping("create")
    public String renderCreateForm(@ModelAttribute ArticleDTO article) {
        return "pages/articles/create";
    }

    //post metoda pro vytvoreni clanku
    @PreAuthorize("isAuthenticated()")
    @PostMapping("create")
    public String createArticle(
            @Valid @ModelAttribute ArticleDTO article,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors())
            return renderCreateForm(article);

        articleService.create(article);
        redirectAttributes.addFlashAttribute("success", "Článek vytvořen.");

        return "redirect:/articles";
    }


//METODA PRO ZOBRAZENI FORMULARE PRO UPRAVU CLANKU - POUZE PRO ADMINISTRATORY
    @Secured("ROLE_ADMIN")
    @GetMapping("edit/{articleId}")
    public String renderEditForm(
            @PathVariable Long articleId,
            ArticleDTO article
    ) {
        ArticleDTO fetchedArticle = articleService.getById(articleId);
        articleMapper.updateArticleDTO(fetchedArticle, article);

        return "pages/articles/edit";
    }

    //post metoda pro upravu clanku
    @Secured("ROLE_ADMIN")
    @PostMapping("edit/{articleId}")
    public String editArticle(
            @PathVariable long articleId,
            @Valid ArticleDTO article,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors())
            return renderEditForm(articleId, article);

        article.setArticleId(articleId);
        articleService.edit(article);
        redirectAttributes.addFlashAttribute("success", "Článek upraven.");

        return "redirect:/articles";
    }


//METODA PRO SMAZANI CLANKU - POUZE PRO ADMINISTRATORY
    @Secured("ROLE_ADMIN")
    @GetMapping("delete/{articleId}")
    public String deleteArticle(
            @PathVariable long articleId,
            RedirectAttributes redirectAttributes
    ) {
        articleService.remove(articleId);
        redirectAttributes.addFlashAttribute("success", "Článek smazán.");

        return "redirect:/articles";
    }

    @ExceptionHandler({ArticleNotFoundException.class})
    public String handleArticleNotFoundException(
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("error", "Článek nenalezen.");
        return "redirect:/articles";
    }
}
