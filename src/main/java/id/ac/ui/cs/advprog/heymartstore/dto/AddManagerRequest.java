package id.ac.ui.cs.advprog.heymartstore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class AddManagerRequest {
    private String name;

    private String email;

    private String password;
}