package br.com.beltis.ergo.controller;

import br.com.beltis.ergo.domain.model.Users;
import br.com.beltis.ergo.dto.UserDTO;
import br.com.beltis.ergo.mapper.UserMapper;
import br.com.beltis.ergo.repository.UserRepository;
import br.com.beltis.ergo.infra.security.dto.LoginRequestDTO;
import br.com.beltis.ergo.infra.security.dto.RegisterRequestDTO;
import br.com.beltis.ergo.infra.security.dto.ResponseDTO;
import br.com.beltis.ergo.infra.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/ergo/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    @GetMapping("/busca-por-email")
    public ResponseEntity<UserDTO> findByEmail(@RequestParam String email) {
        Users users = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (users != null) {
            return ResponseEntity.ok(this.userMapper.convertToUserDTO(users));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO dto){
        Users users = this.userRepository.findByEmail(dto.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(dto.password(), users.getPassword())) {
            String token = this.tokenService.generateToken(users);
            return ResponseEntity.ok(new ResponseDTO(users.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO dto){
        Optional<Users> user = this.userRepository.findByEmail(dto.email());

        if(user.isEmpty()) {
            Users newUsers = new Users();
            newUsers.setPassword(passwordEncoder.encode(dto.password()));
            newUsers.setEmail(dto.email());
            newUsers.setName(dto.name());
            this.userRepository.save(newUsers);

            String token = this.tokenService.generateToken(newUsers);
            return ResponseEntity.ok(new ResponseDTO(newUsers.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

}
