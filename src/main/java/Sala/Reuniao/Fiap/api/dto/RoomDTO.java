package Sala.Reuniao.Fiap.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Capacidade é obrigatória")
    private Integer capacidade;

    @NotBlank(message = "Localização é obrigatória")
    private String localizacao;
}
