package com.example.minijira.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.minijira.dto.authDTO.ForgotRequest;
import com.example.minijira.dto.authDTO.LoginRequest;
import com.example.minijira.dto.authDTO.MeDTO;
import com.example.minijira.dto.authDTO.RegisterRequest;
import com.example.minijira.dto.authDTO.RegisterResponse;
import com.example.minijira.dto.authDTO.TokenResponseDto;
import com.example.minijira.enums.Role;
import com.example.minijira.model.RefreshToken;
import com.example.minijira.model.User;
import com.example.minijira.repository.UserRepository;
import com.example.minijira.util.JwtUtil;


@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;


    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager,JwtUtil jwtUtil,RefreshTokenService refreshTokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService=refreshTokenService;
        this.emailService = emailService;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
       if(userRepository.existsByEmail(registerRequest.getEmail())){
        throw new RuntimeException("Email already exist!");
       }

       if(userRepository.existsByUsername(registerRequest.getUsername())){
        throw new RuntimeException("Try new! buddy...");
       }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Role role;
        switch(registerRequest.getRole().toLowerCase()){
            case "admin" :
                role = Role.ADMIN;
                break;
            
           default :
                role = Role.USER;
        }
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        System.out.println("before email");
       emailService.sendEmail(user.getEmail(), "Welcome to MiniJira", "Hi " + user.getUsername() + ",\n\nWelcome to MiniJira! We're excited to have you on board. If you have any questions or need assistance, feel free to reach out.\n\nBest regards,\nThe MiniJira Team");
        System.out.println("after email");

        return new RegisterResponse(user.getId(),
                                      user.getUsername(),
                                    user.getEmail() 
                                 );
    }
 @Override
public TokenResponseDto login(LoginRequest request) {

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
    );

    // ✅ get authenticated user details
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    // ✅ generate access token properly
    String accessToken = jwtUtil.generateToken(userDetails.getUsername());

    // ✅ create refresh token using authenticated user
    RefreshToken refreshToken = refreshTokenService
            .createRefreshToken(userDetails.getUsername());
            emailService.sendEmail(userRepository.findByUsername(userDetails.getUsername()).get().getEmail(), "Login Alert", "Hi " + userDetails.getUsername() + ",\n\nYou have successfully logged in to MiniJira. If this wasn't you, please secure your account immediately.\n\nBest regards,\nThe MiniJira Team");

    return new TokenResponseDto(
            accessToken,
            refreshToken.getToken()
    );
}

    public String forgot(ForgotRequest forgotRequest) {
        if(!userRepository.existsByEmail(forgotRequest.getEmail())){
            throw new RuntimeException("Email not exist!");
        }

        User user = userRepository.findByEmail(forgotRequest.getEmail())
                                .orElseThrow(() -> new RuntimeException("User not found!"));


       
        if(!passwordEncoder.matches(forgotRequest.getOldPassword(),user.getPassword())){
            throw new RuntimeException("Password Not Matched!");
        }
       
        user.setPassword(passwordEncoder.encode(forgotRequest.getNewPassword()));
        userRepository.save(user);
        return "Password changed successfully!";
    }

    public  MeDTO me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String  username =  authentication.getName();

        User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));

                    return new MeDTO(
                        user.getUsername(),
                        user.getEmail()
                    );
       

        
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String  username =  authentication.getName();

        return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }


   

}
