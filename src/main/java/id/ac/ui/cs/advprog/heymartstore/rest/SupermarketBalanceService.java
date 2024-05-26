package id.ac.ui.cs.advprog.heymartstore.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SupermarketBalanceService {
    private final WebClient webClient;

    public SupermarketBalanceService(@Value("${spring.route.gateway_url}") String gatewayUrl) {
        this.webClient = WebClient.builder().baseUrl(gatewayUrl + "/api/order/supermarket-balance").build();
    }

    public void createBalance(String token, Long supermarketId) {
        webClient.get()
                .uri("/create/" + supermarketId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
