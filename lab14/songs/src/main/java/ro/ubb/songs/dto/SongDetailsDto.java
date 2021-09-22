package ro.ubb.songs.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Data
@Builder
public class SongDetailsDto implements Serializable {
    private String title;

    private int length;

    private Character key;
}
