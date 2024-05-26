package id.ac.ui.cs.advprog.heymartstore.rest;

import id.ac.ui.cs.advprog.heymartstore.dto.RegisterManagerRequest;
import id.ac.ui.cs.advprog.heymartstore.dto.RemoveManagerRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class AuthService {
    private final WebClient webClient;

    public AuthService(@Value("${spring.route.gateway_url}") String gatewayUrl) {
        this.webClient = WebClient.builder().baseUrl(gatewayUrl + "/api/auth").build();
    }

    public boolean registerManager(RegisterManagerRequest request) {
        return Objects.requireNonNull(webClient.post()
                        .uri("/register-manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + request.getAdminToken())
                        .bodyValue(request)
                        .retrieve()
                        .onStatus(HttpStatusCode::is5xxServerError,
                                clientResponse -> clientResponse.bodyToMono(String.class)
                                        .flatMap(body -> Mono.error(new RuntimeException(body))))
                        .toBodilessEntity()
                        .block())
                .getStatusCode()
                .is2xxSuccessful();
    }

    public boolean removeManager(RemoveManagerRequest request) {
        return Objects.requireNonNull(webClient.post()
                        .uri("/remove-manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + request.getAdminToken())
                        .bodyValue(request)
                        .retrieve()
                        .onStatus(HttpStatusCode::is5xxServerError,
                                clientResponse -> clientResponse.bodyToMono(String.class)
                                        .flatMap(body -> Mono.error(new RuntimeException(body))))
                        .toBodilessEntity()
                        .block())
                .getStatusCode()
                .is2xxSuccessful();
    }
}
