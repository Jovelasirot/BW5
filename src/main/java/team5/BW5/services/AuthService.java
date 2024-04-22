package team5.BW5.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team5.BW5.entities.User;
import team5.BW5.exceptions.UnauthorizedException;
import team5.BW5.payloads.UserLoginDTO;
import team5.BW5.security.JwtTools;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUserAndGenerateToken(UserLoginDTO payload) {

        User userFound = this.userService.findByEmail(payload.email());

        if (bcrypt.matches(payload.password(), userFound.getPassword())) {
            return jwtTools.createToken(userFound);
        } else {
            throw new UnauthorizedException("Invalid email or password ლ(ಠ益ಠ)ლ");
        }
    }

}
