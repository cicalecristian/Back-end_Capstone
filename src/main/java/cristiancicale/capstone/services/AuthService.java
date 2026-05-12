package cristiancicale.capstone.services;

import cristiancicale.capstone.entities.User;
import cristiancicale.capstone.exceptions.NotFoundException;
import cristiancicale.capstone.exceptions.UnauthorizedException;
import cristiancicale.capstone.payloads.LoginDTO;
import cristiancicale.capstone.security.TokenTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final TokenTools tokenTools;
    private final PasswordEncoder bcrypt;

    public AuthService(UserService userService, TokenTools tokenTools, PasswordEncoder bcrypt) {

        this.userService = userService;
        this.tokenTools = tokenTools;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        try {
            User found = this.userService.findByEmail(body.email());
            if (this.bcrypt.matches(body.password(), found.getPassword())) {
                return this.tokenTools.generateToken(found);
            } else {
                throw new UnauthorizedException("Credenziali errate");
            }
        } catch (NotFoundException ex) {
            throw new UnauthorizedException("Credenziali errate");
        }
    }
}
