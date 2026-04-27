package Sala.Reuniao.Fiap.api.controller;

import Sala.Reuniao.Fiap.api.domain.Room;
import Sala.Reuniao.Fiap.api.dto.RoomDTO;
import Sala.Reuniao.Fiap.api.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Salas", description = "Gerenciamento de salas de reunião")
@SecurityRequirement(name = "bearerAuth")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    @Operation(summary = "Lista todas as salas com paginação")
    public ResponseEntity<Page<Room>> listar(Pageable pageable) {
        return ResponseEntity.ok(roomService.listar(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca sala por ID")
    public ResponseEntity<Room> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cria uma nova sala")
    public ResponseEntity<Room> criar(@Valid @RequestBody RoomDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.criar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma sala existente")
    public ResponseEntity<Room> atualizar(@PathVariable Long id, @Valid @RequestBody RoomDTO dto) {
        return ResponseEntity.ok(roomService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma sala")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        roomService.remover(id);
        return ResponseEntity.noContent().build();
    }
}