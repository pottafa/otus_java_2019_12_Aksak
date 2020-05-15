package ru.otus.homework.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.homework.dataBase.core.model.User;
import ru.otus.homework.front.FrontendService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
public class AdminPanelController {
    private static final String ADMIN_PANEL_TEMPLATE = "admin-panel";
    private final FrontendService frontendService;
    private SimpMessagingTemplate messagingTemplate;

    public AdminPanelController(FrontendService frontendService, SimpMessagingTemplate messagingTemplate) {
        this.frontendService = frontendService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/admin-panel")
    protected String allUsersView(Model model) {
        CompletableFuture<List<User>> future = new CompletableFuture<>();
        frontendService.getAllUsersData(future::complete);
        model.addAttribute("users", future.join());
        return ADMIN_PANEL_TEMPLATE;
    }

    @MessageMapping("/new_user")
    public void newUser(User user) {
        frontendService.saveUserData(user, consumer -> messagingTemplate.convertAndSend("/topic/newUser", user));
    }


}
