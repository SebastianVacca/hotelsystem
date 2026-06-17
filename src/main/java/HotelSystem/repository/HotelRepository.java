package HotelSystem.repository;

import HotelSystem.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByCiudad(String ciudad);
    List<Hotel> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT DISTINCT h FROM Hotel h JOIN h.habitaciones hab WHERE hab.disponible = true")
    List<Hotel> findByHotelesConHabitacionesDisponibles();
}
