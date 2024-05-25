package id.ac.ui.cs.advprog.heymartstore.dto;

import lombok.Builder;

@Builder
public class RegisterManagerRequest {
    public String name;

    public String email;

    public String password;

    public Long supermarketId;

    public String adminToken;
}
