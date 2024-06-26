package com.group21.repairmenService.services.authentication;

import com.group21.repairmenService.dto.SignupRequestDTO;
import com.group21.repairmenService.dto.UserDto;
import com.group21.repairmenService.entity.User;
import com.group21.repairmenService.enums.UserRole;
import com.group21.repairmenService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    public UserDto signupClient(SignupRequestDTO signupRequestDTO){
        User user = new User();

        user.setName(signupRequestDTO.getName());
        user.setLastname(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));

        user.setRole(UserRole.CLIENT);

        return userRepository.save(user).getDto();
    }

    public Boolean presentByEmail(String email) {
        return  userRepository.findFirstByEmail(email) != null;
    }

    public UserDto signupCompany(SignupRequestDTO signupRequestDTO){
        User user = new User();

        user.setName(signupRequestDTO.getName());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));

        user.setRole(UserRole.COMPANY);

        return userRepository.save(user).getDto();
    }

    public boolean changePassword(Long userId, String verifyPassword, String newPassword) {

       Optional<User> user = userRepository.findById(userId);
       if(!user.isPresent()){
           throw new RuntimeException("User not found!!!.");
       }else {
           BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
           User  actualUser = user.get();
           if(encoder.matches(verifyPassword, actualUser.getPassword())){
               String newEncryptedPassword = encoder.encode(newPassword);
               actualUser.setPassword(newEncryptedPassword);
               userRepository.save(actualUser);
               return true;
           }
       }
       return false;
    }
}
