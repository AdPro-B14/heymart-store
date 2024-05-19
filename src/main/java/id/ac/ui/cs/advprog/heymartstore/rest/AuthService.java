package id.ac.ui.cs.advprog.heymartstore.rest;

import id.ac.ui.cs.advprog.heymartstore.dto.RegisterManagerRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Service
public class AuthService {
    private final WebClient webClient;

    public AuthService(@Value("${spring.route.gateway_url}") String gatewayUrl) {
        this.webClient = WebClient.builder().baseUrl(gatewayUrl + "/api/auth").build();
    }

    public boolean registerManager(RegisterManagerRequest request) {
        return Objects.requireNonNull(webClient.post()
                        .uri("/signup/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + request.adminToken)
                        .body(request, RegisterManagerRequest.class)
                        .retrieve()
                        .toBodilessEntity()
                        .block())
                .getStatusCode()
                .is2xxSuccessful();
    }
}
