package Sala.Reuniao.Fiap.api.service;

import Sala.Reuniao.Fiap.api.domain.Booking;
import Sala.Reuniao.Fiap.api.domain.Room;
import Sala.Reuniao.Fiap.api.dto.BookingDTO;
import Sala.Reuniao.Fiap.api.exception.BusinessException;
import Sala.Reuniao.Fiap.api.repository.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    public BookingService(BookingRepository bookingRepository, RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
    }

    public Page<Booking> listar(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    public Booking criar(BookingDTO dto) {
        if (!dto.getDataHoraFim().isAfter(dto.getDataHoraInicio())) {
            throw new BusinessException("Data/hora de fim deve ser após o início");
        }

        boolean conflito = bookingRepository.existsConflict(
                dto.getRoomId(),
                dto.getDataHoraInicio(),
                dto.getDataHoraFim()
        );

        if (conflito) {
            throw new BusinessException("Já existe uma reserva para esta sala neste horário");
        }

        Room room = roomService.buscarPorId(dto.getRoomId());

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setDataHoraInicio(dto.getDataHoraInicio());
        booking.setDataHoraFim(dto.getDataHoraFim());
        booking.setResponsavel(dto.getResponsavel());

        return bookingRepository.save(booking);
    }

    public void cancelar(Long id) {
        bookingRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Reserva não encontrada"));
        bookingRepository.deleteById(id);
    }
}