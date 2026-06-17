package HotelSystem.repository;

import HotelSystem.model.Habitacion;
import HotelSystem.shared.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findByHotelId(Long id);

    List<Habitacion> findByDisponibleAndHotelId(Boolean disponible, Long hotelId);

    List<Habitacion> findByTipo(TipoHabitacion tipoHabitacion);

    @Query("SELECT h FROM Habitacion h  WHERE h.precioNoche <= :precio ")
    List<Habitacion> findByPrecioNocheLessThanOrEqual(Double precio);
}

