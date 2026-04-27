package Sala.Reuniao.Fiap.api.service;

import Sala.Reuniao.Fiap.api.domain.Room;
import Sala.Reuniao.Fiap.api.dto.RoomDTO;
import Sala.Reuniao.Fiap.api.exception.BusinessException;
import Sala.Reuniao.Fiap.api.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Page<Room> listar(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    public Room buscarPorId(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Sala não encontrada"));
    }

    public Room criar(RoomDTO dto) {
        Room room = new Room();
        room.setNome(dto.getNome());
        room.setCapacidade(dto.getCapacidade());
        room.setLocalizacao(dto.getLocalizacao());
        return roomRepository.save(room);
    }

    public Room atualizar(Long id, RoomDTO dto) {
        Room room = buscarPorId(id);
        room.setNome(dto.getNome());
        room.setCapacidade(dto.getCapacidade());
        room.setLocalizacao(dto.getLocalizacao());
        return roomRepository.save(room);
    }

    public void remover(Long id) {
        buscarPorId(id);
        roomRepository.deleteById(id);
    }
}