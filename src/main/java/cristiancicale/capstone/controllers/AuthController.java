package cristiancicale.capstone.controllers;

import cristiancicale.capstone.entities.User;
import cristiancicale.capstone.exceptions.ValidationException;
import cristiancicale.capstone.payloads.LoginDTO;
import cristiancicale.capstone.payloads.LoginRespDTO;
import cristiancicale.capstone.payloads.UserDTO;
import cristiancicale.capstone.payloads.UserRespDTO;
import cristiancicale.capstone.services.AuthService;
import cristiancicale.capstone.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        return new LoginRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
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
}
