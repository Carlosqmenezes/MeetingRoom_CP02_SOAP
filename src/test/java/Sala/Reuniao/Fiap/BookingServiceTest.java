package Sala.Reuniao.Fiap;

import Sala.Reuniao.Fiap.api.domain.Booking;
import Sala.Reuniao.Fiap.api.domain.Room;
import Sala.Reuniao.Fiap.api.dto.BookingDTO;
import Sala.Reuniao.Fiap.api.exception.BusinessException;
import Sala.Reuniao.Fiap.api.repository.BookingRepository;
import Sala.Reuniao.Fiap.api.service.BookingService;
import Sala.Reuniao.Fiap.api.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private BookingService bookingService;

    private Room room;
    private BookingDTO dto;

    @BeforeEach
    void setup() {
        room = new Room();
        room.setId(1L);
        room.setNome("Sala A");
        room.setCapacidade(10);
        room.setLocalizacao("Bloco 1");

        dto = new BookingDTO();
        dto.setRoomId(1L);
        dto.setDataHoraInicio(LocalDateTime.now().plusHours(1));
        dto.setDataHoraFim(LocalDateTime.now().plusHours(2));
        dto.setResponsavel("João");
    }

    @Test
    void deveCriarReservaSemConflito() {
        when(bookingRepository.existsConflict(
                dto.getRoomId(),
                dto.getDataHoraInicio(),
                dto.getDataHoraFim()
        )).thenReturn(false);

        when(roomService.buscarPorId(dto.getRoomId())).thenReturn(room);

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setDataHoraInicio(dto.getDataHoraInicio());
        booking.setDataHoraFim(dto.getDataHoraFim());
        booking.setResponsavel(dto.getResponsavel());

        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking resultado = bookingService.criar(dto);

        assertNotNull(resultado);
        assertEquals("João", resultado.getResponsavel());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void deveLancarExcecaoQuandoHouverConflito() {
        when(bookingRepository.existsConflict(
                dto.getRoomId(),
                dto.getDataHoraInicio(),
                dto.getDataHoraFim()
        )).thenReturn(true);

        BusinessException ex = assertThrows(BusinessException.class, () -> {
            bookingService.criar(dto);
        });

        assertEquals("Já existe uma reserva para esta sala neste horário", ex.getMessage());
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoFimForAntesDoInicio() {
        dto.setDataHoraFim(LocalDateTime.now().plusMinutes(30));
        dto.setDataHoraInicio(LocalDateTime.now().plusHours(2));

        BusinessException ex = assertThrows(BusinessException.class, () -> {
            bookingService.criar(dto);
        });

        assertEquals("Data/hora de fim deve ser após o início", ex.getMessage());
    }
}