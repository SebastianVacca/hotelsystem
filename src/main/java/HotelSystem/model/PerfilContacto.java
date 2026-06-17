package HotelSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "perfil_contactos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilContacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telefono;

    private String telefonoEmergencia;

    private String direccion;

    private String pais;

    private String nacionalidad;

    @JsonIgnore
    @OneToOne(mappedBy = "perfilContacto")
    private Huesped huesped;
}
