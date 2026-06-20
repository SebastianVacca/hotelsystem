package HotelSystem.controller;

import HotelSystem.model.Reserva;
import HotelSystem.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reserva")
public class ReservaController {
    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<Reserva>> listarReservas() {
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> traerPorId(@PathVariable Long id) {
        return reservaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/huesped/{huespedId}")
    public ResponseEntity<List<Reserva>> traerReservasPorHuesped(@PathVariable Long huespedId) {
        return ResponseEntity.ok(reservaService.listaPorHuesped(huespedId));
    }

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        Reserva nuevaReserva = reservaService.crear(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@RequestBody Reserva reserva, @PathVariable Long id) {
        Reserva actualizarReserva = reservaService.actualizar(reserva, id);
        return ResponseEntity.ok(actualizarReserva);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Reserva> cancelarReserva(@PathVariable Long id) {
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/servicios/{servicioId}")
    public ResponseEntity<Reserva> agregarServicio(@PathVariable Long id, @PathVariable Long servicioId) {
        Reserva reserva = reservaService.agregarServicio(id, servicioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

}
