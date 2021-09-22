package ro.ubb.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class ReviewDto extends BaseDto<Integer>{

    private UserDto user;
    private SongDto song;
    private int stars;
    private String reviewText;
}
