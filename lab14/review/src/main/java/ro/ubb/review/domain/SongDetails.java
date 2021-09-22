package ro.ubb.review.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SongDetails implements Serializable {

    private String title;

    private int length;

    private Character key;
}
