package ro.ubb.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.core.domain.User;
import ro.ubb.web.dto.UserDto;

@Component
public class UserConverter extends BaseConverter<User, UserDto>{
    @Override
    public User convertDtoToModel(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .build();
        user.setId(userDto.getId());
        return user;
    }

    @Override
    public UserDto convertModelToDto(User user) {
        UserDto dto = UserDto.builder()
                .name(user.getName())
                .build();
        dto.setId(user.getId());
        return dto;
    }
}
