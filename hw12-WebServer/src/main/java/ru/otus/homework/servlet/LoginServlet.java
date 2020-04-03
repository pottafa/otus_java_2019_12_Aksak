package ru.otus.homework.servlet;

import ru.otus.homework.services.TemplateProcessor;
import ru.otus.homework.services.UserAuthService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class LoginServlet extends HttpServlet {

    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final int MAX_INACTIVE_INTERVAL = 30;


    private final TemplateProcessor templateProcessor;
    private final UserAuthService userAuthService;

    public LoginServlet(TemplateProcessor templateProcessor, UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
        this.templateProcessor = templateProcessor;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);

        if (userAuthService.authenticate(name, password)) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
            response.sendRedirect("/admin-panel");
        } else {
            response.setStatus(SC_UNAUTHORIZED);
        }

    }

}
