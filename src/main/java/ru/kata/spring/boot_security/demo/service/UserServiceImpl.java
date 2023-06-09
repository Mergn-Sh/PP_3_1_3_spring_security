package ru.kata.spring.boot_security.demo.service;

import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user;
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void saveUser(User user){
        userRepository.save(user);
    }

    @Override
    public User getUser(Long id){
        User user = new User();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) user = optionalUser.get();
        return user;
    }

    @Transactional
    @Override
    public void updateUser(User user){
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
