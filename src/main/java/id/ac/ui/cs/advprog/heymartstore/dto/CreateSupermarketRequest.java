package id.ac.ui.cs.advprog.heymartstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSupermarketRequest {
    private String name;
}
