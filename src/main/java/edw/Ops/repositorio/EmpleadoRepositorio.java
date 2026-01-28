package edw.Ops.repositorio;

import edw.Ops.modelo.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;


    public interface EmpleadoRepositorio extends JpaRepository<Empleado, Integer> {
}
