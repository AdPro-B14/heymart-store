package id.ac.ui.cs.advprog.heymartstore.rest;

import id.ac.ui.cs.advprog.heymartstore.dto.RegisterManagerRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthService {
    private final WebClient webClient;

    public AuthService(@Value("${spring.route.gateway_url}") String gatewayUrl) {
    }

    public boolean registerManager(RegisterManagerRequest request) {
        return false;
    }
}
