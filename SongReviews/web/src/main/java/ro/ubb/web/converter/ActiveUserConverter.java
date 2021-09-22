package ro.ubb.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.core.service.stastisticEntities.ActiveUser;
import ro.ubb.web.dto.ActiveUserDto;

@Component
public class ActiveUserConverter extends BaseConverter<ActiveUser, ActiveUserDto> {

    @Autowired
    UserConverter converter;

    @Override
    public ActiveUser convertDtoToModel(ActiveUserDto activeUserDto) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ActiveUserDto convertModelToDto(ActiveUser activeUser) {
        return ActiveUserDto.builder()
                .user(converter.convertModelToDto(activeUser.getUser()))
                .activity(activeUser.getActivity())
                .build();
    }
}
