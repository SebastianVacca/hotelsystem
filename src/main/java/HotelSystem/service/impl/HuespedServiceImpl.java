package HotelSystem.service.impl;

import HotelSystem.exception.AlreadyExistException;
import HotelSystem.exception.ResourceNotFoundException;
import HotelSystem.model.Huesped;
import HotelSystem.repository.HuespedRepository;
import HotelSystem.service.HuespedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HuespedServiceImpl implements HuespedService {

    private final HuespedRepository huespedRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Huesped> listarTodas() {
        return huespedRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Huesped> buscarPorId(Long id) {
        return huespedRepository.findById(id);
    }

    @Override
    @Transactional
    public Huesped crear(Huesped huesped) {
        var existe = huespedRepository.findByDocumentoIdentidad(huesped.getDocumentoIdentidad());
        if(existe.isPresent())
            throw new AlreadyExistException("Huesped ya registrado");
        return huespedRepository.save(huesped);
    }

    @Override
    @Transactional
    public Huesped actualizar(Huesped huesped, Long id) {
        Huesped entity = huespedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el huesped solicitado"));
        entity.setNombre(huesped.getNombre());
        entity.setApellido(huesped.getApellido());
        entity.setDocumentoIdentidad(huesped.getDocumentoIdentidad());
        entity.setEmail(huesped.getEmail());
        entity.setPerfilContacto(huesped.getPerfilContacto());

        return huespedRepository.save(entity);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if(!huespedRepository.existsById(id))
            throw new ResourceNotFoundException("No se encuentra el huesped solicitado");
        huespedRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Huesped> buscarPorNombreOApellido(String criterio) {
        return huespedRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(criterio, criterio);
    }
}
