package real_estate_agency_app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import real_estate_agency_app.utils.MyFileReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/error-register-offer") //some problems occur when the path is just "/error"
public class ErrorController {
    private static final String ERROR_PAGE_RELATIVE_PATH = "static/error.html";

    private HttpServletRequest request;
    private MyFileReader myFileReader;

    @Autowired
    public ErrorController(HttpServletRequest request, MyFileReader myFileReader) {
        this.request = request;
        this.myFileReader = myFileReader;
    }

    @GetMapping
    @SuppressWarnings(value = "unchecked")
    @ResponseBody
    public String getError() throws IOException {
        List<String> errorMessages =
                ((ArrayList<String>)this.request.getSession().getAttribute("error-messages"))
                .stream()
                .map(e -> this.envelopeMessage(e))
                .collect(Collectors.toList());

        String htmlContent = this.myFileReader.getContentFromRelativePath(ERROR_PAGE_RELATIVE_PATH);
        return htmlContent.replace("{{error-messages}}", String.join("\n", errorMessages));
    }

    private String envelopeMessage(String message) {
        return String.format("<p class=\"error-message\">%s</p>", message);
    }
}
