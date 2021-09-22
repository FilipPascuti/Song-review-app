package ro.ubb.web.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BaseDto<ID extends Serializable> implements Serializable {
    private ID id;
}
