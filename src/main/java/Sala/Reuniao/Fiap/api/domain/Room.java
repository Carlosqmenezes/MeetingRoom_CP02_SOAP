package Sala.Reuniao.Fiap.api.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "Capacidade é obrigatória")
    @Column(nullable = false)
    private Integer capacidade;

    @NotBlank(message = "Localização é obrigatória")
    @Column(nullable = false)
    private String localizacao;
}