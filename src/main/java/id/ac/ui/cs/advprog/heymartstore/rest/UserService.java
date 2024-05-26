package id.ac.ui.cs.advprog.heymartstore.rest;

import id.ac.ui.cs.advprog.heymartstore.dto.GetProfileResponse;
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
public class UserService {
    private final WebClient webClient;

    public UserService(@Value("${spring.route.gateway_url}") String gatewayUrl) {
        this.webClient = WebClient.builder().baseUrl(gatewayUrl + "/api/user").build();
    }

    public GetProfileResponse getProfile(String token) {
        return webClient.get()
                .uri("/profile")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(GetProfileResponse.class)
                .block();
    }
}
