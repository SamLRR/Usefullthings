package com.infotrans.osk.usefullthings.controller;

import com.infotrans.osk.usefullthings.domain.Role;
import com.infotrans.osk.usefullthings.domain.User;
import com.infotrans.osk.usefullthings.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String userList(Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("{id}")
    public String userEditForm(@PathVariable Long id, Model model) {
        User userFromDB = userRepo.getOne(id);
        model.addAttribute("user", userFromDB);
        model.addAttribute("rolesTL",Role.values());
        return "editUser";
    }

    @PostMapping("{userId}")
    public String update(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @PathVariable("userId") User user) {
        user.setUsername(username);
        user.setActive(false);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
            if(key.equals("active")){
                user.setActive(true);
            }
        }

        userRepo.save(user);
        return "redirect:/user";
    }
}