package HotelSystem.service;

import HotelSystem.model.Huesped;
import HotelSystem.model.Reserva;

import java.util.List;
import java.util.Optional;

public interface HuespedService {
    List<Huesped> listarTodas();
    Optional<Huesped> buscarPorId(Long id);
    Huesped crear(Huesped huesped);
    Huesped actualizar(Huesped huesped, Long id);
    void eliminar(Long id);
    List<Huesped> buscarPorNombreOApellido(String criterio);
}
