package Sala.Reuniao.Fiap.api.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingDTO {

    @NotNull(message = "Sala é obrigatória")
    private Long roomId;

    @NotNull(message = "Data/hora de início é obrigatória")
    @FutureOrPresent(message = "Início deve ser no presente ou futuro")
    private LocalDateTime dataHoraInicio;

    @NotNull(message = "Data/hora de fim é obrigatória")
    @Future(message = "Fim deve ser no futuro")
    private LocalDateTime dataHoraFim;

    @NotBlank(message = "Responsável é obrigatório")
    private String responsavel;
}