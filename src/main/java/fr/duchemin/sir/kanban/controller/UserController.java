package fr.duchemin.sir.kanban.controller;

import fr.duchemin.sir.kanban.dto.UserDTO;
import fr.duchemin.sir.kanban.entity.User;
import fr.duchemin.sir.kanban.message.Response;
import fr.duchemin.sir.kanban.message.ResponseMessageType;
import fr.duchemin.sir.kanban.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "User")
@RestController
@RequestMapping(value = "/api")
public class UserController {

    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = this.userService.getAllUsers()
                .stream()
                .map(user -> this.modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "id") Long userId) {
        User user = this.userService.getUserById(userId);
        UserDTO userResponse = this.modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        User userRequest = this.modelMapper.map(userDTO, User.class);
        User user = this.userService.createUser(userRequest);
        UserDTO userResponse = this.modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable(value = "id") Long userId, @RequestBody @Valid UserDTO userDTO) {
        User userRequest = this.modelMapper.map(userDTO, User.class);
        User user = this.userService.updateUser(userId, userRequest);
        UserDTO userResponse = this.modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Response> removeUserById(@PathVariable(value = "id") Long userId) {
        this.userService.deleteUser(userId);

        Response response = new Response(HttpStatus.OK, ResponseMessageType.SUCCESS.toString());
        response.addDetail("user", "The user has been removed.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
