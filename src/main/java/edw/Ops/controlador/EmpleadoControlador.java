package edw.Ops.controlador;

import  edw.Ops.excepcion.RecursoNoEncontradoExcepcion;
import  edw.Ops.modelo.Empleado;
import  edw.Ops.servicio.IEmpleadoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//http://localhost:8080/OpsApplication/
@RequestMapping("/empleados")


@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://recursosh-frontend.onrender.com"
})


public class EmpleadoControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(EmpleadoControlador.class);

    @Autowired
    private IEmpleadoServicio empleadoServicio;

    // http://localhost:8080/OpsApplication/empleados
    @GetMapping("/empleados")
    public List<Empleado> obtenerEmpleados(){
        var empleados = empleadoServicio.listarEmpleados();
        empleados.forEach((empleado -> logger.info(empleado.toString())));
        return empleados;
    }

    @PostMapping("/empleados")
    public Empleado agregarEmpleado(@RequestBody Empleado empleado){
        logger.info("Empleado a agregar: " + empleado);
        return empleadoServicio.guardarEmpleado(empleado);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado>
    obtenerEmpleadoPorId(@PathVariable Integer id){
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        return ResponseEntity.ok(empleado);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado>
    actualizarEmpleado(@PathVariable Integer id,
                       @RequestBody Empleado empleadoRecibido){
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if (empleado == null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        empleado.setNombre(empleadoRecibido.getNombre());
        empleado.setDepartamento(empleadoRecibido.getDepartamento());

        empleado.setEmail(empleadoRecibido.getEmail());
        empleado.setTelefono(empleadoRecibido.getTelefono());
        empleado.setCiudad(empleadoRecibido.getCiudad());

        empleado.setSalario(empleadoRecibido.getSalario());
        empleadoServicio.guardarEmpleado(empleado);
        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>>
    eliminarEmpleado(@PathVariable Integer id){
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado == null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        empleadoServicio.eliminarEmpleado(empleado);
        // Json {"eliminado": "true"}
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}


