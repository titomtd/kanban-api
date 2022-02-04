package fr.duchemin.sir.kanban.service;

import fr.duchemin.sir.kanban.entity.Address;
import fr.duchemin.sir.kanban.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long userId);

    User createUser(User user);

    User updateUser(Long userId, User user);

    void deleteUser(Long userId);

    User setAddressToUser(Long userId, Address address);
}
