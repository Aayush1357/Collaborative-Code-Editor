package com.collaborativecode.RegisterUserMicroservice.Service;

import com.collaborativecode.RegisterUserMicroservice.Repository.UserRepository;
import com.collaborativecode.RegisterUserMicroservice.codegenerator.SnowflakeIdGenerator;
import com.collaborativecode.RegisterUserMicroservice.dto.LoginUserRequest;
import com.collaborativecode.RegisterUserMicroservice.dto.UserDTO;
import com.collaborativecode.RegisterUserMicroservice.dto.VerifyUserRequest;
import com.collaborativecode.RegisterUserMicroservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationService {


    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final RestTemplate restTemplate = new RestTemplate();

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    public AuthenticationService(SnowflakeIdGenerator snowflakeIdGenerator) {
        this.snowflakeIdGenerator = snowflakeIdGenerator;
    }

    public ResponseEntity<?> signup(UserDTO userDTO){

        if(userRepository.findByEmail(userDTO.getEmail()).isPresent())
            return ResponseEntity.badRequest().body("Email already in use");

        if(userRepository.findByUsername(userDTO.getUsername()).isPresent())
            return ResponseEntity.badRequest().body("Username already in use");

        User user = new User();
        user.setId(snowflakeIdGenerator.nextId());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setVerification_code(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        sendVerificationEmail(user);
        userRepository.save(user);
        return ResponseEntity.ok().body("User Registered Successfully");
    }

    private void sendVerificationEmail(User user) {
        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE " + user.getVerification_code();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail() , subject , htmlMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }


    public User authenticate(LoginUserRequest input_data) throws Exception{
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input_data.getEmail(),
                        input_data.getPassword()
                )
        );

        User user = (User) auth.getPrincipal();

        if (!user.isEnabled()){
            return null;
        }
        return user;
    }

    public void verifyUser(VerifyUserRequest input) {
        Optional<User> optionalUser = userRepository.findByEmail(input.getEmail());

        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())){
                throw new RuntimeException("Verification code has expired");
            }

            if(user.getVerification_code().equals(input.getVerificationCode())){

                user.setVerification_code(null);
                user.setVerificationCodeExpiresAt(null);
                user.setEnabled(true);

                userRepository.save(user);
            }else{
                throw new RuntimeException("Invalid verification code.");
            }
        }else{
            throw new RuntimeException("User not found.");
        }
    }
}
