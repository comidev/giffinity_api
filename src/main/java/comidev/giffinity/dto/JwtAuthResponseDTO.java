package comidev.giffinity.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtAuthResponseDTO {
    private String token;
    private String type = "Bearer";

    public JwtAuthResponseDTO(String token, String type) {
        this.token = token;
        this.type = type;
    }

    public JwtAuthResponseDTO(String token) {
        this.token = token;
    }
}
