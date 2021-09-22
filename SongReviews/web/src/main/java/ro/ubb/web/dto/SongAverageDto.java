package ro.ubb.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Data
@Builder
public class SongAverageDto {
    private SongDto song;
    private Integer average;
}
