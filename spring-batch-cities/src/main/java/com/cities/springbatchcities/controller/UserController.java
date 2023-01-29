package com.cities.springbatchcities.controller;

import com.cities.springbatchcities.common.UserConstant;
import com.cities.springbatchcities.dao.User;
import com.cities.springbatchcities.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/user/join")
    public String joinGroup(@RequestBody User user) {
        user.setRoles(UserConstant.DEFAULT_ROLE);//USER
        String encryptedPwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPwd);
        repository.save(user);
        return "Hi " + user.getUserName() + " welcome to group !";
    }

    @GetMapping("/access/{userId}/{userRole}")
    //@Secured("ROLE_ALLOW_EDIT")
    @PreAuthorize("hasAuthority('ROLE_ALLOW_EDIT')")
    public String giveAccessToUser(@PathVariable int userId, @PathVariable String userRole, Principal principal) {
        User user = repository.findById(userId).get();
        List<String> activeRoles = getRolesByLoggedInUser(principal);
        String newRole = "";
        if (activeRoles.contains(userRole)) {
            newRole = user.getRoles() + "," + userRole;
            user.setRoles(newRole);
        }
        repository.save(user);
        return "Hi " + user.getUserName() + " New Role assign to you by " + principal.getName();
    }


    @GetMapping("/user/roles/{username}")
    @PreAuthorize("hasAuthority('ROLE_ALLOW_EDIT') or hasAuthority('ROLE_USER')")
    public User getUserByName(@PathVariable String username) {
        return repository.findByUserName(username).get();
    }

    @GetMapping()
    @Secured("ROLE_ALLOW_EDIT")
    @PreAuthorize("hasAuthority('ROLE_ALLOW_EDIT')")
    public List<User> loadUsers() {
        return repository.findAll();
    }

    @GetMapping("/user/hasEditAuthority")
    @Secured("ROLE_ALLOW_EDIT")
    @PreAuthorize("hasAuthority('ROLE_ALLOW_EDIT')")
    public String hasAdminAuthority() {
        return "Has allow editing authority";
    }

    @GetMapping("/user/test")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String testUserAccess() {
        return "user can only access this !";
    }

    private List<String> getRolesByLoggedInUser(Principal principal) {
        String roles = getLoggedInUser(principal).getRoles();
        List<String> assignRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
        if (assignRoles.contains("ROLE_ALLOW_EDIT")) {
            return Arrays.stream(UserConstant.ADMIN_ACCESS).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private User getLoggedInUser(Principal principal) {
        return repository.findByUserName(principal.getName()).get();
    }
}
