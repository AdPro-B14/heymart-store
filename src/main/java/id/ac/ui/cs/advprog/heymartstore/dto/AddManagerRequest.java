package id.ac.ui.cs.advprog.heymartstore.dto;

import lombok.Builder;

@Builder
public class AddManagerRequest {
    public String name;

    public String email;

    public String password;
}
