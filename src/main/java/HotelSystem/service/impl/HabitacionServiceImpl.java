package HotelSystem.service.impl;

import HotelSystem.exception.ResourceNotFoundException;
import HotelSystem.model.Habitacion;
import HotelSystem.model.Hotel;
import HotelSystem.repository.HabitacionRepository;
import HotelSystem.repository.HotelRepository;
import HotelSystem.service.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HotelRepository hotelRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Habitacion> listarTodas() {
        return habitacionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Habitacion> buscarPorId(Long id) {
        return habitacionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habitacion> listarPorHotel(Long hotelId) {
        return habitacionRepository.findByHotelId(hotelId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habitacion> listarDisponiblesPorHotel(Long hotelId) {
        return habitacionRepository.findByDisponibleAndHotelId(true, hotelId);
    }

    @Override
    @Transactional
    public Habitacion crear(Habitacion habitacion, Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel no encontrado"));
        habitacion.setHotel(hotel);
        return habitacionRepository.save(habitacion);
    }

    @Override
    @Transactional
    public Habitacion actualizar(Habitacion habitacion, Long hotelId) {
        if (!habitacionRepository.existsById(habitacion.getId()))
            throw new ResourceNotFoundException("Habitación no encontrada");

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel no encontrado"));
        habitacion.setHotel(hotel);
        return habitacionRepository.save(habitacion);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!habitacionRepository.existsById(id))
            throw new ResourceNotFoundException("Habitación no encontrada");
        habitacionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habitacion> buscarPorPrecioMaximo(Double precioMaximo) {
        return habitacionRepository.findByPrecioNocheLessThanOrEqual(precioMaximo);
    }
}
