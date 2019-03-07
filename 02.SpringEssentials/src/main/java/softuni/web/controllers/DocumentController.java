package softuni.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/document")
public class DocumentController extends BaseController {
    public DocumentController(HttpSession httpSession) {
        super(httpSession);
    }
}
