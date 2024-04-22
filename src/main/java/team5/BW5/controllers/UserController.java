package team5.BW5.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team5.BW5.entities.User;
import team5.BW5.exceptions.BadRequestException;
import team5.BW5.payloads.UserDTO;
import team5.BW5.payloads.UserResponseDTO;
import team5.BW5.services.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO saveUser(@RequestBody @Validated UserDTO payload, BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        return new UserResponseDTO(this.userService.save(payload).getId());
    }

    @PostMapping("/profile/avatar/upload/{userId}")
    public UserResponseDTO uploadAvatar(@PathVariable Long userId, @RequestParam("image") MultipartFile image) throws IOException {
        this.userService.uploadProfileImage(userId, image);
        return new UserResponseDTO(userId);
    }

    @GetMapping
    public Page<User> getAllUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.userService.getUsers(page, size, sortBy);
    }

    @GetMapping("{userId}")
    public User getUser(@PathVariable Long userId) {
        return this.userService.findById(userId);
    }

    @PutMapping("{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User userBody) {
        return this.userService.updateById(userId, userBody);
    }

    @DeleteMapping("{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteById(userId);
    }

}
