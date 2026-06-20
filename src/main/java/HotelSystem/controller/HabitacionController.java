package HotelSystem.controller;

import HotelSystem.model.Habitacion;
import HotelSystem.service.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class HabitacionController {
    private final HabitacionService habitacionService;

    @GetMapping("/api/habitaciones")
    public ResponseEntity<List<Habitacion>> listarHabitaciones() {
        return ResponseEntity.ok(habitacionService.listarTodas());
    };

    @GetMapping("/api/habitaciones/{id}")
    public ResponseEntity<Habitacion> buscarHabitacion(@PathVariable Long id) {
        return habitacionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    };

    @GetMapping("/api/hotels/{hotelId}/habitaciones/disponibles")
    public ResponseEntity<List<Habitacion>> listaHabitacionesDisponibles(@PathVariable Long hotelId) {
        return ResponseEntity.ok(habitacionService.listarDisponiblesPorHotel(hotelId));
    };

    @GetMapping("/api/habitaciones/precio-maximo")
    public ResponseEntity<List<Habitacion>> precioMaximo(@RequestParam("precio") Double precio) {
        return ResponseEntity.ok(habitacionService.buscarPorPrecioMaximo(precio));
    };

    @PostMapping("/api/hotels/{hotelId}/habitaciones")
    public ResponseEntity<Habitacion> crear(@RequestBody Habitacion habitacion, @PathVariable Long hotelId) {
        Habitacion nuevaHabitacion = habitacionService.crear(habitacion, hotelId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaHabitacion);
    };

    @PutMapping("/api/habitaciones/{id}")
    public ResponseEntity<Habitacion> actualizar(@RequestBody Habitacion habitacion, @PathVariable Long id) {
        habitacion.setId(id);
        Long hotelId = (habitacion.getHotel() != null)
                ? habitacion.getHotel().getId()
                : null;
        Habitacion actaulizaHabitacion = habitacionService.actualizar(habitacion, hotelId);
        return ResponseEntity.ok(actaulizaHabitacion);
    };

    @DeleteMapping("/api/habitaciones/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        habitacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    };
}
