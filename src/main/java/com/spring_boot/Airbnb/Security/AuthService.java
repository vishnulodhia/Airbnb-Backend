package com.spring_boot.Airbnb.Security;

import com.spring_boot.Airbnb.Dto.LoginDto;
import com.spring_boot.Airbnb.Dto.SignupRequestDto;
import com.spring_boot.Airbnb.Dto.UserDto;
import com.spring_boot.Airbnb.Exceptions.ResourceNotFoundExceptions;
import com.spring_boot.Airbnb.Model.Role;
import com.spring_boot.Airbnb.Model.User;
import com.spring_boot.Airbnb.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserDto signUp(SignupRequestDto signupRequestDto){
        Optional<User> user = userRepository.findByEmail(signupRequestDto.getEmail());

        if(user.isPresent()){
            throw new RuntimeException("User is already present with same email id");
        }

        User newUser = mapper.map(signupRequestDto,User.class);
        newUser.setRoles(Set.of(Role.GUEST));
        newUser.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        newUser = userRepository.save(newUser);

        return mapper.map(newUser,UserDto.class);
    }

    public String[] login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        String[] arr = new String[2];
        arr[0] = jwtService.generateAccessToken(user);
        arr[1] = jwtService.generateRefersToken(user);
        return arr;
    }

    public String generateRefreshToken(String refreshToken ){
        Long id = jwtService.getUserIdFromToken(refreshToken);

        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("User not found"));
        return jwtService.generateAccessToken(user);
    }


}
