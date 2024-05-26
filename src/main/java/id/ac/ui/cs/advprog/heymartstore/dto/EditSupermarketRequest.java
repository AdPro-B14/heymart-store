package id.ac.ui.cs.advprog.heymartstore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class EditSupermarketRequest {
    private String name;
    private List<String> managers;

    @Setter
    private String adminToken;
}
