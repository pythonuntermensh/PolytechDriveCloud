package com.polytech.drive.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/me")
public class UserController {
    public String getMePage(@ModelAttribute("user") String user) {
        return "";
    }
}
