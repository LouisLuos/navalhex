package br.com.navalhex.modules.user.service;

import br.com.navalhex.modules.user.dto.RegisterDTO;
import br.com.navalhex.modules.user.entity.UserEntity;
import br.com.navalhex.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    
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

    

}
