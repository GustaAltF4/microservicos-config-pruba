package com.usuario_microservice.controller;

import com.usuario_microservice.entity.Usuario;
import com.usuario_microservice.models.AutoDto;
import com.usuario_microservice.models.MotoDto;
import com.usuario_microservice.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id){
        Usuario user = usuarioService.getUsuarioById(id);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
        Usuario user = usuarioService.save(usuario);
        return ResponseEntity.ok(user);
    }

    //-----------endpoints de vehículos-----------
    @GetMapping("/autos/{usuarioId}")
    public ResponseEntity<List<AutoDto>> listarAutos(@PathVariable("usuarioId") int usuarioId){
        Usuario user= usuarioService.getUsuarioById(usuarioId);
        if (user == null){
            return ResponseEntity.noContent().build();
        }
        List<AutoDto> autos = usuarioService.getAutos(usuarioId);
        return ResponseEntity.ok(autos);
    }

    @GetMapping("/motos/{usuarioId}")
    public ResponseEntity<List<MotoDto>> listarMotos(@PathVariable("usuarioId") int usuarioId){
        Usuario user= usuarioService.getUsuarioById(usuarioId);
        if (user == null){
            return ResponseEntity.noContent().build();
        }
        List<MotoDto> motos = usuarioService.getMotos(usuarioId);
        return ResponseEntity.ok(motos);
    }

    @PostMapping("/auto/{usuarioId}")
    public ResponseEntity<AutoDto> guardarAuto(@PathVariable("usuarioId") int usuarioId, @RequestBody AutoDto auto) {
        AutoDto nuevoAuto= usuarioService.saveAuto(usuarioId,auto);
        return ResponseEntity.ok(nuevoAuto);
    }

    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<MotoDto> guardarMoto(@PathVariable("usuarioId") int usuarioId, @RequestBody MotoDto moto) {
        MotoDto nuevaMoto= usuarioService.saveMoto(usuarioId,moto);
        return ResponseEntity.ok(nuevaMoto);
    }

    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> ListarTodosLosVehiculos(@PathVariable("usuarioId") int usuarioId){
        Map<String, Object> response = usuarioService.getUsuarioAndVehiculos(usuarioId);
        return ResponseEntity.ok(response);

    }


}
