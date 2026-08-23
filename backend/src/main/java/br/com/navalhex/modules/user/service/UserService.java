package br.com.navalhex.modules.user.service;

import br.com.navalhex.modules.user.dto.LoginDTO;
import br.com.navalhex.modules.user.dto.LoginResponseDTO;
import br.com.navalhex.modules.user.dto.RegisterDTO;
import br.com.navalhex.modules.user.entity.UserEntity;
import br.com.navalhex.modules.user.repository.UserRepository;
import br.com.navalhex.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    
    
    public void createUser(RegisterDTO registerDTO){
        if (userRepository.existsByEmail(registerDTO.email())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (userRepository.existsByWhatsapp(registerDTO.whatsapp())) {
            throw new IllegalArgumentException("Whatsapp já cadastrado");
        }
        String hashedPassword = passwordEncoder.encode(registerDTO.password());
        UserEntity user = UserEntity.builder()
    .name(registerDTO.name())
    .email(registerDTO.email())
    .whatsapp(registerDTO.whatsapp())
    .password(hashedPassword)
    .role(registerDTO.role())
    .build();

    userRepository.save(user);
    }

    public LoginResponseDTO login(LoginDTO loginDTO){
        UserEntity user = userRepository.findByEmail(loginDTO.email())
        .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos"));

        if(!passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }

        String token = tokenService.generateToken(user);
        return new LoginResponseDTO(token, user.getName(), user.getEmail(), user.getRole());
    }

    

}
