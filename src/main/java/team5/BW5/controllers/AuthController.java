package team5.BW5.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team5.BW5.exceptions.BadRequestException;
import team5.BW5.payloads.UserDTO;
import team5.BW5.payloads.UserLoginDTO;
import team5.BW5.payloads.UserLoginResponseDTO;
import team5.BW5.payloads.UserResponseDTO;
import team5.BW5.services.AuthService;
import team5.BW5.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO registerUser(@RequestBody @Validated UserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return new UserResponseDTO(this.userService.save(body).getId());
        }
    }

    @PostMapping("/login")
    public UserLoginResponseDTO loginUser(@RequestBody @Validated UserLoginDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new UserLoginResponseDTO(this.authService.authenticateUserAndGenerateToken(body));
    }


}
