package fr.duchemin.sir.kanban.service;

import fr.duchemin.sir.kanban.entity.User;
import fr.duchemin.sir.kanban.exception.EntityNotFoundException;
import fr.duchemin.sir.kanban.exception.InternalServerException;
import fr.duchemin.sir.kanban.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        this.userRepository.findAll().iterator().forEachRemaining(users::add);
        return users;
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);

        if (userOptional.isEmpty())
            throw new EntityNotFoundException("User with id " + userId + " not found.");

        return userOptional.get();
    }

    @Override
    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) {
        Optional<User> userOptional = this.userRepository.findById(userId);

        if (userOptional.isEmpty())
            throw new EntityNotFoundException("User with id " + userId + " not found.");

        User userResponse = userOptional.get();

        if (null != user.getFirstName())
            userResponse.setFirstName(user.getFirstName());

        if (null != user.getLastName())
            userResponse.setLastName(user.getLastName());

        if (null == userResponse.getAddress()) {
            userResponse.setAddress(user.getAddress());
        } else {
            userResponse.getAddress().setStreet(user.getAddress().getStreet());
            userResponse.getAddress().setCity(user.getAddress().getCity());
            userResponse.getAddress().setZipCode(user.getAddress().getZipCode());
        }

        return this.userRepository.save(userResponse);
    }

    @Override
    public void deleteUser(Long userId) {
        try {
            this.userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException exception) {
            throw new EntityNotFoundException("User with id " + userId + " not found.");
        }

        if (this.userRepository.existsById(userId))
            throw new InternalServerException("Failed : The user hasn't been removed.");
    }
}
