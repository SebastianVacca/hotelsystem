package HotelSystem.repository;

import HotelSystem.model.Reserva;
import HotelSystem.shared.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByHuespedId(Long id);
    List<Reserva> findByEstado(EstadoReserva estado);
    @Query("SELECT r FROM Reserva r WHERE r.fechaIngreso >= :fechaInicio AND r.fechaIngreso <= :fechaFin")
    List<Reserva> findByFechaIngreso(LocalDate fechaInicio, LocalDate fechaFin);
    @Query("SELECT r FROM Reserva r WHERE r.habitacion.id = :habitacionId AND r.estado IN :estadosValidos")
    List<Reserva> findReservasActivas(Long habitacionId, List<EstadoReserva> estadosValidos);//TODO implementar la lista con una funcion en el servicio
}
