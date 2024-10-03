package com.usuario_microservice.servicio;

import com.usuario_microservice.entity.Usuario;
import com.usuario_microservice.feignClient.AutoFeignClient;
import com.usuario_microservice.feignClient.MotoFeignClient;
import com.usuario_microservice.models.AutoDto;
import com.usuario_microservice.models.MotoDto;
import com.usuario_microservice.repository.UsuarioRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AutoFeignClient autoFeignClient;
    @Autowired
    private MotoFeignClient motoFeignClient;

    //-----------métodos de vehículos-----------
    //Rest template para Métodos GET
    @CircuitBreaker(name = "autosCB", fallbackMethod = "fallbackAutos")
    public List<AutoDto> getAutos(int usuarioId){
        List<AutoDto> autos = restTemplate.getForObject(
                "http://auto-microservice/auto/usuario/" + usuarioId,
                List.class);
        return autos;
    }
    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallbackMotos")
    public List<MotoDto> getMotos(int usuarioId){
        List<MotoDto> motos = restTemplate.getForObject(
                "http://moto-microservice/moto/usuario/" + usuarioId,
                List.class);
        return motos;
    }

    //Feign para Métodos POST y GET de todos los vehículos
    @CircuitBreaker(name = "autosCB", fallbackMethod = "fallbackAutos")
    public AutoDto saveAuto(int usuarioId,AutoDto auto){
        auto.setUsuarioId(usuarioId);
       AutoDto nuevoAuto= autoFeignClient.save(auto);
       return nuevoAuto;
//        return autoFeignClient.save(auto);
    }
    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallbackMotos")
    public MotoDto saveMoto(int usuarioId,MotoDto moto){
        moto.setUsuarioId(usuarioId);
        MotoDto nuevaMoto= motoFeignClient.save(moto);
        return nuevaMoto;
    }
    @CircuitBreaker(name = "todosCB", fallbackMethod = "fallbackTodos")
    public Map<String,Object> getUsuarioAndVehiculos(int usuarioId){
        Map<String, Object> response = new LinkedHashMap<>();
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null){
            response.put("Mensaje", "El usuario no existe");
            return response;
        }
        response.put("usuario", usuario);
        List<AutoDto> autos= autoFeignClient.getAutos(usuarioId);
        if (autos==null||autos.isEmpty()){
            response.put("Autos", "El usuario no tiene autos");
        }else {
            response.put("Autos", autos);
        }
        List<MotoDto> motos= motoFeignClient.getMotos(usuarioId);
        if (motos==null||motos.isEmpty()){
            response.put("Motos", "El usuario no tiene motos");
        }else {
            response.put("Motos", motos);
        }
        return response;

    }



    //-----------métodos de usuario-----------

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(int id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    //-----------métodos de fallback-----------
    public List<AutoDto> fallbackAutos(int usuarioId, Throwable t){
        System.out.println("Fallback Autos Error: "+t.getMessage());
        List<AutoDto> fallbackResponse = new ArrayList<>();
        AutoDto mensaje= new AutoDto();
        mensaje.setModelo("Error: No se pudieron obtener los autos. Servicio no disponible.");
        fallbackResponse.add(mensaje);
        return fallbackResponse;

    }
    public List<MotoDto> fallbackMotos(int usuarioId, Throwable t){
        System.out.println("Fallback Motos Error: "+t.getMessage());
        List<MotoDto> fallbackResponse = new ArrayList<>();
        MotoDto mensaje= new MotoDto();
        mensaje.setModelo("Error: No se pudieron obtener las motos. Servicio no disponible.");
        fallbackResponse.add(mensaje);
        return fallbackResponse;

    }
    public Map<String,Object> fallbackTodos(int usuarioId, Throwable t){
        System.out.println("Fallback Todos Error: "+t.getMessage());
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("Mensaje", "Error de comunicación con los servicios de autos o motos. Por favor, inténtelo más tarde.");
        response.put("usuarioId", usuarioId);
        return response;
    }

}
