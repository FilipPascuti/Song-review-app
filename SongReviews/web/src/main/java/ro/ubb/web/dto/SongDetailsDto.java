package ro.ubb.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Data
@Builder
public class SongDetailsDto {
    private String title;

    private int length;

    private Character key;
}
