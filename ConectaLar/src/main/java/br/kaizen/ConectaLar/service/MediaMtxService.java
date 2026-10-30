
package br.kaizen.ConectaLar.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

@Service
public class MediaMtxService {

    @Value("${mediamtx.api.base-url}")
    private String apiUrl;

    @Value("${mediamtx.stream.path-prefix:camera}")
    private String prefixoCaminho;

    private final RestClient restClient = RestClient.create();

    public String gerarCaminho(Long cameraId) {
        return prefixoCaminho + cameraId;
    }

    public boolean estaTransmitindo(String caminho) {

        try {
            JsonNode resposta = restClient.get()
                    .uri(apiUrl + "/v3/paths/list")
                    .retrieve()
                    .body(JsonNode.class);

            if (resposta == null || !resposta.has("items")) {
                return false;
            }

            JsonNode itens = resposta.get("items");

            for (JsonNode item : itens) {

                String nome = item.path("name").asText();
                boolean online = item.path("online").asBoolean(false);
                boolean ready = item.path("ready").asBoolean(false);

                if (nome.equals(caminho)) {
                    return online && ready;
                }
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }
}
