package ro.ubb.songs.domain;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Artist implements Serializable {

    private Integer id;

    private String name;

    private String description;
}
