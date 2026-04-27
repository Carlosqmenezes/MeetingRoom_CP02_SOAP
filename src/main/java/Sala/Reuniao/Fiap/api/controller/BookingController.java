package Sala.Reuniao.Fiap.api.controller;

import Sala.Reuniao.Fiap.api.domain.Booking;
import Sala.Reuniao.Fiap.api.dto.BookingDTO;
import Sala.Reuniao.Fiap.api.service.BookingService;
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
@RequestMapping("/api/bookings")
@Tag(name = "Reservas", description = "Gerenciamento de reservas")
@SecurityRequirement(name = "bearerAuth")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    @Operation(summary = "Lista todas as reservas com paginação")
    public ResponseEntity<Page<Booking>> listar(Pageable pageable) {
        return ResponseEntity.ok(bookingService.listar(pageable));
    }

    @PostMapping
    @Operation(summary = "Cria uma nova reserva")
    public ResponseEntity<Booking> criar(@Valid @RequestBody BookingDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.criar(dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancela uma reserva")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        bookingService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}