package id.ac.ui.cs.advprog.heymartstore.dto;

import lombok.Builder;

@Builder
public class GetProfileResponse {
    public Long id;
    public String email;
    public String name;
    public String role;
}
