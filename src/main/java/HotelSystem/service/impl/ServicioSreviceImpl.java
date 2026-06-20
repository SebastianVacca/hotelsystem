package HotelSystem.service.impl;

import HotelSystem.exception.ResourceNotFoundException;
import HotelSystem.model.Servicio;
import HotelSystem.repository.ServicioRepository;
import HotelSystem.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicioSreviceImpl implements ServicioService {
    private final ServicioRepository servicioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> listarDisponibles() {
        return servicioRepository.findByDisponible(true);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Servicio> buscarPorId(Long id) {
        return  servicioRepository.findById(id);
    }

    @Override
    @Transactional
    public Servicio crear(Servicio servicio) {
        servicio.setDisponible(true);
        return servicioRepository.save(servicio);
    }

    @Override
    @Transactional
    public Servicio actualizar(Long servicioId, Servicio servicio) {
        Servicio entity = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));
        if (servicio.getNombre() != null) entity.setNombre(servicio.getNombre());
        if (servicio.getDisponible() != null) entity.setDisponible(servicio.getDisponible());
        if (servicio.getPrecio() != null) entity.setPrecio(servicio.getPrecio());
        if (servicio.getDescripcion() != null) entity.setDescripcion(servicio.getDescripcion());

        return servicioRepository.save(entity);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Servicio entity = servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));
        entity.setDisponible(false);
        servicioRepository.save(entity);
    }
}
