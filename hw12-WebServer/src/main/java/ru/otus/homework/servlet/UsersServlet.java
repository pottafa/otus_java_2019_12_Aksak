package ru.otus.homework.servlet;

import ru.otus.homework.dataBase.core.service.DBServiceUser;
import ru.otus.homework.dataBase.core.model.User;
import ru.otus.homework.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class UsersServlet extends HttpServlet {
       private static final String ADMIN_PAGE_TEMPLATE = "admin-panel.html";

    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;

    public UsersServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        List<User> users = dbServiceUser.getAllUsers();
        paramsMap.put("usersList", users);
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, paramsMap));
    }



}
