package HotelSystem.controller;

import HotelSystem.model.Hotel;
import HotelSystem.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotel")
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<Hotel>> listarHoteles(@RequestParam(required = false) String ciudad) {
        var request = (ciudad != null)
                ? ResponseEntity.ok(hotelService.buscarPorCiudad(ciudad))
                : ResponseEntity.ok(hotelService.buscarPorCiudad(null));
        return request;
    };

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> traerPorId(@PathVariable Long id) {
        return hotelService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    };

    @GetMapping("/disponibles")
    public ResponseEntity<List<Hotel>> buscarHotelesConHabitacionesDisponibles() {
        return ResponseEntity.ok(hotelService.buscarConHabitacionesDisponibles());
    };

    @PostMapping
    public ResponseEntity<Hotel> crearHotel(@RequestBody Hotel hotel) {
        Hotel nuevoHotel = hotelService.crear(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHotel);
    };

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> actualizarHotel(@RequestBody Hotel hotel, @PathVariable Long id) {
        Hotel actualizarHotel = hotelService.actualizar(hotel, id);
        return ResponseEntity.ok(actualizarHotel);
    };

    @DeleteMapping("/{id}")
    public ResponseEntity<Hotel> eliminarHotel(@PathVariable Long id) {
        hotelService.eliminar(id);
        return ResponseEntity.noContent().build();
    };
}
