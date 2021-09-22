package ro.ubb.review.domain;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class User implements Serializable {
    private Integer id;
    private String name;
}
