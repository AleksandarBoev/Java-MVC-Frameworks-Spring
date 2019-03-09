package softuni.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.domain.models.service.DocumentServiceModel;
import softuni.domain.models.view.DocumentHomeAllView;
import softuni.service.DocumentService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {
    private static final int DOCUMENTS_PER_PAGE = 5;

    private DocumentService documentService;

    @Autowired
    public HomeController(HttpSession httpSession, DocumentService documentService) {
        super(httpSession);
        this.documentService = documentService;
    }

    @GetMapping
    public String getIndexPage() {
        if (super.isLoggedIn())
            return "redirect:/home";

        return "index";
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(ModelAndView modelAndView) {
        if (!super.isLoggedIn()) {
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        List<DocumentHomeAllView> documentHomeAllViews = this.documentService.getAll()
                .stream()
                .map(d -> {
                    DocumentHomeAllView result = new DocumentHomeAllView();
                    result.setTrimmedTitle(d.getTitle());
                    result.setId(d.getId());
                    return result;
                }).collect(Collectors.toList());

        modelAndView.addObject("documents", this.reformat(documentHomeAllViews, DOCUMENTS_PER_PAGE));

        modelAndView.setViewName("home");
        return modelAndView;
    }

    private List<DocumentHomeAllView[]> reformat(List<DocumentHomeAllView> documents, int docsPerRow) {
        List<DocumentHomeAllView[]> result = new ArrayList<>();

        int counter = 0;
        for (DocumentHomeAllView document : documents) {
            if (counter % docsPerRow == 0) {
                DocumentHomeAllView[] newRow = new DocumentHomeAllView[docsPerRow];
                result.add(newRow);
            }

            result.get(counter / docsPerRow)[counter % docsPerRow] = document;
            counter++;
        }

        return result;
    }
}
