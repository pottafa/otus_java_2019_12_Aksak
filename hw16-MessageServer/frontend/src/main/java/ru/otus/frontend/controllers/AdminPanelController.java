package ru.otus.frontend.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.frontend.front.FrontendService;
import ru.otus.common.model.*;

@Controller
public class AdminPanelController {
    private static final String ADMIN_PANEL_TEMPLATE = "admin-panel";
    private final FrontendService frontendService;
    private final SimpMessagingTemplate messagingTemplate;

    public AdminPanelController(FrontendService frontendService, SimpMessagingTemplate messagingTemplate) {
        this.frontendService = frontendService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/admin-panel")
    protected String allUsersView(Model model) {
        return ADMIN_PANEL_TEMPLATE;
    }

    @MessageMapping("/new_user")
    public void newUser(User user) {
        frontendService.saveUserData(user, consumer -> messagingTemplate.convertAndSend("/topic/newUser", user));
    }

    @SubscribeMapping("/getUsers")
    public void getUsers() {
        frontendService.getAllUsersData(usersList -> messagingTemplate.convertAndSend("/topic/getUsers", usersList));
    }


}
