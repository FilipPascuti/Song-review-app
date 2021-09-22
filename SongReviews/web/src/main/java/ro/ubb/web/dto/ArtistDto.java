package ro.ubb.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Builder
public class ArtistDto extends BaseDto<Integer>{
    private String name;
    private String description;
}
