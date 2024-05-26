package id.ac.ui.cs.advprog.heymartstore.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterManagerRequest {
    private String name;

    private String email;

    private String password;

    private Long supermarketId;

    private String adminToken;
}
