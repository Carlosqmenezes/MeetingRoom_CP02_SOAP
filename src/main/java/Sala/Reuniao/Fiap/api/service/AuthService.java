package Sala.Reuniao.Fiap.api.service;

import Sala.Reuniao.Fiap.api.domain.User;
import Sala.Reuniao.Fiap.api.dto.LoginDTO;
import Sala.Reuniao.Fiap.api.dto.TokenDTO;
import Sala.Reuniao.Fiap.api.exception.BusinessException;
import Sala.Reuniao.Fiap.api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenDTO login(LoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("Senha inválida");
        }

        String token = jwtService.gerarToken(user.getUsername());
        return new TokenDTO(token, "Bearer", user.getUsername());
    }
}