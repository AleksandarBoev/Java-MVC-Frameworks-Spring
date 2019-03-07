package softuni.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public abstract class BaseController {
    private HttpSession httpSession;

    @Autowired
    protected BaseController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    protected HttpSession getHttpSession() {
        return this.httpSession;
    }

    protected void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    protected boolean isLoggedIn() {
        return this.httpSession.getAttribute("user") != null;
    }
}
