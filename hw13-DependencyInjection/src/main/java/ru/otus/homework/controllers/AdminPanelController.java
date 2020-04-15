package ru.otus.homework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.homework.dataBase.core.model.User;
import ru.otus.homework.dataBase.core.service.DBServiceUser;

import java.io.IOException;
import java.util.List;

@Controller
public class AdminPanelController {
  private static final String ADMIN_PANEL_TEMPLATE = "admin-panel.html";
  private static final String USER_CREATE_TEMPLATE = "user-create.html";
  private final DBServiceUser dbServiceUser;

  public AdminPanelController(DBServiceUser dbServiceUser) {
    this.dbServiceUser = dbServiceUser;
  }


  @GetMapping("/admin-panel/save")
  public String userView(Model model) {
    model.addAttribute("user", new User());
    return USER_CREATE_TEMPLATE;
  }

  @PostMapping("/admin-panel/save")
  public RedirectView userSave(@ModelAttribute User user) {
    dbServiceUser.saveUser(user);
    return new RedirectView("/admin-panel", true);
  }

  @GetMapping("/admin-panel")
  protected String allUsersView(Model model) {
    List<User> users = dbServiceUser.getAllUsers();
    model.addAttribute("users", users);
    return ADMIN_PANEL_TEMPLATE;
  }

}
