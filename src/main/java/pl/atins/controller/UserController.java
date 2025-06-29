package pl.atins.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.atins.domain.User;
import pl.atins.service.UserService;

/**
 * @author Serhii Manko
 */
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    public static final String REDIRECT_USER_URL = "redirect:/users";
    private final UserService userService;

    @GetMapping
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public String getAllUsers(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sort,
                              @AuthenticationPrincipal User user) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<User> userPage = userService.findAllUsers(pageable);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentUserId", user.getId());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortField", sort);

        return "users/list";
    }

    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        User existingUser = userService.findUserById(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        userService.saveUser(existingUser);
        return REDIRECT_USER_URL;
    }

    @PostMapping("/{id}/enable")
    public String enableUser(@PathVariable Long id) {
        userService.enableUser(id);
        return REDIRECT_USER_URL;
    }

    @PostMapping("/{id}/disable")
    public String disableUser(@PathVariable Long id) {
        userService.disableUser(id);
        return REDIRECT_USER_URL;
    }
}
