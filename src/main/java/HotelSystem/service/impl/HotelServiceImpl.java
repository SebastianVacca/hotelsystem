package HotelSystem.service.impl;

import HotelSystem.exception.ResourceNotFoundException;
import HotelSystem.model.Hotel;
import HotelSystem.repository.HotelRepository;
import HotelSystem.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> listarTodos() {
        return hotelRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Hotel> buscarPorId(Long id) {
        return hotelRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> buscarPorCiudad(String ciudad) {
        return hotelRepository.findByCiudad(ciudad);
    }

    @Override
    @Transactional
    public Hotel crear(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    @Transactional
    public Hotel actualizar(Hotel hotel, Long id) {
        Hotel model = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El hotel ingresado no existe"));

        model.setNombre(hotel.getNombre());
        model.setCiudad(hotel.getCiudad());
        model.setDireccion(hotel.getDireccion());
        model.setTelefono(hotel.getTelefono());
        model.setCategoria(hotel.getCategoria());

        return hotelRepository.save(model);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if(!hotelRepository.existsById(id))
            throw new  ResourceNotFoundException("El hotel ingresado no existe");
        hotelRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> buscarConHabitacionesDisponibles() {
        return hotelRepository.findByHotelesConHabitacionesDisponibles();
    }
}
