package br.com.beltis.ergo.controller;

import br.com.beltis.ergo.domain.model.ApplicationUser;
import br.com.beltis.ergo.domain.model.Usuario;
import br.com.beltis.ergo.domain.model.enums.UserRole;
import br.com.beltis.ergo.infra.security.dto.LoginRequestDTO;
import br.com.beltis.ergo.infra.security.dto.RegisterRequestDTO;
import br.com.beltis.ergo.infra.security.dto.ResponseDTO;
import br.com.beltis.ergo.infra.security.dto.UserDTO;
import br.com.beltis.ergo.infra.security.service.TokenService;
import br.com.beltis.ergo.mapper.UserMapper;
import br.com.beltis.ergo.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/ergo/auth")
public class AuthController {

    @Autowired
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final UserMapper userMapper;

    @GetMapping("/busca-por-login")
    public ResponseEntity<UserDTO> findByLogin(@RequestParam String login) {
        ApplicationUser users = this.applicationUserRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User not found"));
        if (users != null) {
            return ResponseEntity.ok(this.userMapper.convertToUserDTO(users));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO dto){
        ApplicationUser users = this.applicationUserRepository.findByLogin(dto.login()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(dto.password(), users.getPassword())) {
            String token = this.tokenService.generateToken(users);
            return ResponseEntity.ok(new ResponseDTO(users.getLogin(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO dto){
        Optional<ApplicationUser> user = this.applicationUserRepository.findByLogin(dto.login());

        if(user.isEmpty()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
            ApplicationUser newUsers = new ApplicationUser(dto.name(), dto.login(), encryptedPassword, UserRole.USER);
            this.applicationUserRepository.save(newUsers);

            String token = this.tokenService.generateToken(newUsers);
            return ResponseEntity.ok(new ResponseDTO(newUsers.getLogin(), token));
        }
        return ResponseEntity.badRequest().build();
    }

}
