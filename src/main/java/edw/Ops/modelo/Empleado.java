package edw.Ops.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
 @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
 public class Empleado {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idEmpleado;
     String nombre;
     String departamento;
    private String email;
    private String telefono;
    private String ciudad;
     Double salario;

}
