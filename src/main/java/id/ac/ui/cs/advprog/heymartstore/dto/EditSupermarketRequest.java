package id.ac.ui.cs.advprog.heymartstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EditSupermarketRequest {
    private String name;
    private List<String> managers;
    private String adminToken;
}
