package HotelSystem.service;

import HotelSystem.model.Reserva;

import java.util.List;
import java.util.Optional;

public interface ReservaService {
    List<Reserva> listarTodas();
    Optional<Reserva> buscarPorId(Long id);
    Reserva crear(Reserva reserva);
    Reserva actualizar(Reserva reserva, Long id);
    void cancelar(Long id);
    List<Reserva> listaPorHuesped(Long huespedId);
    Reserva agregarServicio(Long reservaId, Long servicioId);
}
