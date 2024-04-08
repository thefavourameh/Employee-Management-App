package org.favour.employeemgtapp.admin.controller;

import lombok.RequiredArgsConstructor;
import org.favour.employeemgtapp.admin.dto.AdminDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminHomeController {
    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("admin", new AdminDto());
        return "admin-login";
    }
    @GetMapping("/admin-signup")
    public String signup(Model model) {
        model.addAttribute("admin", new AdminDto());
        return "admin-signup";
    }
}
