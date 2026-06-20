package HotelSystem.service;

import HotelSystem.model.Servicio;

import java.util.List;
import java.util.Optional;

public interface ServicioService {
    List<Servicio> listarTodos();
    List<Servicio> listarDisponibles();
    Optional<Servicio> buscarPorId(Long id);
    Servicio crear(Servicio servicio);
    Servicio actualizar(Long id, Servicio servicio);
    void eliminar(Long id);
}
