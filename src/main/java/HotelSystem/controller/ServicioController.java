package HotelSystem.controller;

import HotelSystem.model.Servicio;
import HotelSystem.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/servicio")
public class ServicioController {
    private final ServicioService  servicioService;

    @GetMapping
    public ResponseEntity<List<Servicio>> listarTodos() {
        return ResponseEntity.ok(servicioService.listarTodos());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Servicio>> listarDisponibles() {
        return ResponseEntity.ok(servicioService.listarDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> buscarPorId(@PathVariable Long id) {
        return servicioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Servicio> crear(@RequestBody Servicio servicio) {
        Servicio nuevoServicio = servicioService.crear(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoServicio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizar(@PathVariable Long id, @RequestBody Servicio servicio) {
        Servicio actualizarServicio = servicioService.actualizar(id, servicio);
        return ResponseEntity.ok(actualizarServicio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Servicio> eliminar(@PathVariable Long id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
