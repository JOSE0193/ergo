package br.com.beltis.ergo.mapper;

import br.com.beltis.ergo.domain.model.Users;
import br.com.beltis.ergo.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO convertToUserDTO(Users users){
        return new UserDTO(users.getName(), users.getEmail());
    }
}
