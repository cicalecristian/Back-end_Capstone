package cristiancicale.capstone.controllers;

import cristiancicale.capstone.entities.User;
import cristiancicale.capstone.enums.RoleUser;
import cristiancicale.capstone.exceptions.ValidationException;
import cristiancicale.capstone.payloads.UserDTO;
import cristiancicale.capstone.payloads.UserRespDTO;
import cristiancicale.capstone.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRespDTO saveUser(@RequestBody @Validated UserDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }

        User newUser = this.userService.save(body);
        return new UserRespDTO(newUser.getId(), newUser.getUsername(), newUser.getEmail(), newUser.getName(),
                newUser.getSurname(), newUser.getDateOfBirth(), newUser.getRole(), newUser.getAvatar());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "email") String sortBy) {
        return this.userService.findAll(page, size, sortBy);
    }

    @GetMapping("/me")
    public User getOwnProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public UserRespDTO updateOwnProfile(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody @Validated UserDTO body) {

        User updatedUser = userService.findByIdAndUpdate(currentAuthenticatedUser.getId(), body);

        return new UserRespDTO(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail(),
                updatedUser.getName(), updatedUser.getSurname(), updatedUser.getDateOfBirth(), updatedUser.getRole(), updatedUser.getAvatar());
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwnProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        this.userService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getById(@PathVariable UUID userId) {
        return this.userService.findById(userId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserRespDTO getByIdAndUpdate(@PathVariable UUID userId, @RequestBody @Validated UserDTO body) {

        User updatedUser = userService.findByIdAndUpdate(userId, body);

        return new UserRespDTO(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail(), updatedUser.getName(),
                updatedUser.getSurname(), updatedUser.getDateOfBirth(), updatedUser.getRole(), updatedUser.getAvatar());
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable UUID userId) {
        this.userService.findByIdAndDelete(userId);
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public UserRespDTO changeRole(@PathVariable UUID id, @RequestParam RoleUser roleUser) {

        User updatedUser = userService.changeRole(id, roleUser);

        return new UserRespDTO(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail(),
                updatedUser.getName(), updatedUser.getSurname(), updatedUser.getDateOfBirth(), updatedUser.getRole(), updatedUser.getAvatar());
    }

    @PatchMapping("/avatar")
    public UserRespDTO uploadAvatar(@RequestParam("avatar") MultipartFile file, @AuthenticationPrincipal User currentUser) {

        User updatedUser = userService.avatarUpload(file, currentUser.getId());

        return new UserRespDTO(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail(),
                updatedUser.getName(), updatedUser.getSurname(), updatedUser.getDateOfBirth(), updatedUser.getRole(), updatedUser.getAvatar());
    }
}
