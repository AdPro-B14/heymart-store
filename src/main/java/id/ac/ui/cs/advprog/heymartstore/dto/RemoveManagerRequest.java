package id.ac.ui.cs.advprog.heymartstore.dto;

import lombok.Builder;

@Builder
public class RemoveManagerRequest {
    public String email;

    public Long supermarketId;

    public String adminToken;
}
