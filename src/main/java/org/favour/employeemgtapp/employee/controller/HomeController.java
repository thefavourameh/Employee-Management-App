package org.favour.employeemgtapp.employee.controller;

import lombok.RequiredArgsConstructor;
import org.favour.employeemgtapp.employee.dto.EmployeeDto;
import org.favour.employeemgtapp.employee.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("employee", new EmployeeDto());
        model.addAttribute("loginDto", new LoginDto());
        return "index";
    }
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("employee", new EmployeeDto());
        model.addAttribute("loginDto", new LoginDto());
        return "index";
    }
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("employee", new EmployeeDto());
        model.addAttribute("loginDto", new LoginDto());
        return "employee-signup";
    }


}
