package ro.ubb.web.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Builder
public class UserDto extends BaseDto<Integer>{

    private String name;
}
