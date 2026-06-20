package HotelSystem.service;

import HotelSystem.model.Habitacion;

import java.util.List;
import java.util.Optional;

public interface HabitacionService {
    List<Habitacion> listarTodas();
    Optional<Habitacion> buscarPorId(Long id);
    List<Habitacion> listarPorHotel(Long hotelId);
    List<Habitacion> listarDisponiblesPorHotel(Long hotelId);
    Habitacion crear(Habitacion habitacion, Long hotelId);
    Habitacion actualizar(Habitacion habitacion, Long id);
    void eliminar(Long id);
    List<Habitacion> buscarPorPrecioMaximo(Double precioMaximo);
}
