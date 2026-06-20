package HotelSystem.controller;

import HotelSystem.model.Huesped;
import HotelSystem.service.HuespedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/huespedes")
public class HuespedController {
    private final HuespedService huespedService;

    @GetMapping
    public ResponseEntity<List<Huesped>> listarTodos(){
        return ResponseEntity.ok(huespedService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Huesped> tarerPorId(@PathVariable Long id){
        return huespedService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Huesped>> buscarPorNombreOApellido(@RequestParam String criterio){
        return ResponseEntity.ok(huespedService.buscarPorNombreOApellido(criterio));
    }

    @PostMapping
    public ResponseEntity<Huesped> crear(@RequestBody Huesped huesped){
        Huesped nuevoHuesped = huespedService.crear(huesped);
        return  ResponseEntity.status(HttpStatus.CREATED).body(nuevoHuesped);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Huesped> actualizar(@PathVariable Long id, @RequestBody Huesped huesped){
        Huesped actualizarHuesped = huespedService.actualizar(huesped, id);
        return  ResponseEntity.status(HttpStatus.CREATED).body(actualizarHuesped);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Huesped> eliminar(@PathVariable Long id){
        huespedService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
