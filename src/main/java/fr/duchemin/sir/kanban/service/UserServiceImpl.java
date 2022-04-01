package fr.duchemin.sir.kanban.service;

import fr.duchemin.sir.kanban.entity.Address;
import fr.duchemin.sir.kanban.entity.User;
import fr.duchemin.sir.kanban.exception.EntityNotFoundException;
import fr.duchemin.sir.kanban.exception.InternalServerException;
import fr.duchemin.sir.kanban.repository.AddressRepository;
import fr.duchemin.sir.kanban.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AddressRepository addressRepository;

    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
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

    @Override
    public User setAddressToUser(Long userId, Address address) {
        Optional<User> userOptional = this.userRepository.findById(userId);

        if (userOptional.isEmpty())
            throw new EntityNotFoundException("User with id " + userId + " not found.");

        User userResponse = userOptional.get();
        userResponse.setAddress(address);

        return this.userRepository.save(userResponse);
    }

    @Override
    public User deleteAddressToUser(Long userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);

        if (userOptional.isEmpty())
            throw new EntityNotFoundException("User with id " + userId + " not found.");

        User userResponse = userOptional.get();
        userResponse.setAddress(null);

        return this.userRepository.save(userResponse);
    }
}
