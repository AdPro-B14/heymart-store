package id.ac.ui.cs.advprog.heymartstore.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RemoveManagerRequest {
    private String email;

    private Long supermarketId;

    private String adminToken;
}
