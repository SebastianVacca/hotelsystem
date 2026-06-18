package HotelSystem.service;

import HotelSystem.model.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    List<Hotel> listarTodos();
    Optional<Hotel> buscarPorId(Long id);
    List<Hotel> buscarPorCiudad(String ciudad);
    Hotel crear(Hotel hotel);
    Hotel actualizar(Hotel hotel);
    void eliminar(Long id);
    List<Hotel> buscarConHabitacionesDisponibles();
}
