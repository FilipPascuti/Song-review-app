package ro.ubb.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ActiveUserDto {
    private UserDto user;
    private Integer activity;
}
