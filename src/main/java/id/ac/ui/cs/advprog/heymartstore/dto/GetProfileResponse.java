package id.ac.ui.cs.advprog.heymartstore.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetProfileResponse {
    private Long id;
    private String email;
    private String name;
    private String role;
}