package id.ac.ui.cs.advprog.heymartstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EditSupermarketRequest {
    public String name;
    public List<String> managers;
    public String adminToken;
}
