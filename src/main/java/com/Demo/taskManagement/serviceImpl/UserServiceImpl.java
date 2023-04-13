package com.Demo.taskManagement.serviceImpl;

import com.Demo.taskManagement.entity.Users;
import com.Demo.taskManagement.payload.UserDto;
import com.Demo.taskManagement.repository.UserRepository;
import com.Demo.taskManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDto createUser(UserDto userDto) {
        // UserDTO is not an Entity of Users
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Users user = userDtoToEntity(userDto);  // Converted UserDTO to Users Class
        Users savedUser = userRepository.save(user);
        return entityToUserDto(savedUser);
    }
    private Users userDtoToEntity(UserDto userDto){
        Users users = new Users();
        users.setName(userDto.getName());
        users.setEmail(userDto.getEmail());
        users.setPassword(userDto.getPassword());
        return users;
    }
    private UserDto entityToUserDto(Users savedUser){
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setName(savedUser.getName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setPassword(savedUser.getPassword());
        return userDto;
    }
}
