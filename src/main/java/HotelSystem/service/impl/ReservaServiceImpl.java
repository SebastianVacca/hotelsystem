package HotelSystem.service.impl;

import HotelSystem.exception.ResourceNotFoundException;
import HotelSystem.model.Habitacion;
import HotelSystem.model.Reserva;
import HotelSystem.model.Servicio;
import HotelSystem.repository.HabitacionRepository;
import HotelSystem.repository.ReservaRepository;
import HotelSystem.repository.ServicioRepository;
import HotelSystem.service.ReservaService;
import HotelSystem.shared.EstadoReserva;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ServicioRepository servicioRepository;
    private final HabitacionRepository habitacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    @Transactional
    public Reserva crear(Reserva reserva) {

        Habitacion habitacion = habitacionRepository.findById(reserva.getHabitacion().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada"));

        if (!habitacion.getDisponible())
            throw new IllegalStateException("La habitación seleccionada no se encuentra disponible");

        long noches = ChronoUnit.DAYS.between(reserva.getFechaIngreso(), reserva.getFechaSalida());
        if (noches <= 0)
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de ingreso.");

        reserva.setTotalCalculado(noches * habitacion.getPrecioNoche());
        reserva.setEstado(EstadoReserva.PENDIENTE);

        habitacion.setDisponible(false);
        habitacionRepository.save(habitacion);

        return reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public Reserva actualizar(Reserva reserva) {
        Reserva entity = reservaRepository.findById(reserva.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));

        if (reserva.getFechaIngreso() != null) {
            entity.setFechaIngreso(reserva.getFechaIngreso());
        }

        if (reserva.getFechaSalida() != null) {
            entity.setFechaSalida(reserva.getFechaSalida());
        }

        if (reserva.getEstado() != null) {
            entity.setEstado(reserva.getEstado());
        }

        if (reserva.getFechaIngreso() != null || reserva.getFechaSalida() != null) {
            long noches = ChronoUnit.DAYS.between(entity.getFechaIngreso(), entity.getFechaSalida());
            if (noches <= 0)
                throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de ingreso.");

            entity.setTotalCalculado(noches * entity.getHabitacion().getPrecioNoche());
        }

        return reservaRepository.save(entity);
    }

    @Override
    @Transactional
    public void cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.setEstado(EstadoReserva.CANCELADA);

        Habitacion habitacion = reserva.getHabitacion();
        if (habitacion != null) {
            habitacion.setDisponible(true);
            habitacionRepository.save(habitacion);
        }
        reservaRepository.save(reserva);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> listaPorHuesped(Long huespedId) {
        return reservaRepository.findByHuespedId(huespedId);
    }

    @Override
    @Transactional
    public Reserva agregarServicio(Long reservaId, Long servicioId) {
        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));

        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));

        reserva.getServicios().add(servicio);
        reserva.setTotalCalculado(reserva.getTotalCalculado() + servicio.getPrecio());

        return reservaRepository.save(reserva);
    }
}
