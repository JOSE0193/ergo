package br.com.beltis.ergo.mapper;

import br.com.beltis.ergo.domain.model.ApplicationUser;
import br.com.beltis.ergo.domain.model.Usuario;
import br.com.beltis.ergo.infra.security.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO convertToUserDTO(ApplicationUser user){
        return new UserDTO(user.getName(), user.getLogin(), user.getRole().getValor());
    }
}
