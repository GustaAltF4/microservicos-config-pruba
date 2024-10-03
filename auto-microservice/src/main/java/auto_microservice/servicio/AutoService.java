package auto_microservice.servicio;

import auto_microservice.entity.Auto;
import auto_microservice.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService {

    @Autowired
    private AutoRepository autoRepository;

    public List<Auto> getAll(){
        return autoRepository.findAll();
    }

    public Auto getAutoById(int id){
        return autoRepository.findById(id).orElse(null);
    }

    public Auto save(Auto auto){
        return autoRepository.save(auto);
    }

    public List<Auto> getUsuarioById(int UsuarioId){
        return autoRepository.findByUsuarioId(UsuarioId);
    }
}
