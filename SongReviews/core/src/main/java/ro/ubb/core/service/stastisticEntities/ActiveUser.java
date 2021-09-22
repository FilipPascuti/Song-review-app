package ro.ubb.core.service.stastisticEntities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.ubb.core.domain.User;

@Data
@AllArgsConstructor
@Builder
public class ActiveUser {
    private User user;
    private Integer activity;
}
