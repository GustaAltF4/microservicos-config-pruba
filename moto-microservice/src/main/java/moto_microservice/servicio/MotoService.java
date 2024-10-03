package moto_microservice.servicio;

import moto_microservice.entity.Moto;
import moto_microservice.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> getAll(){
        return motoRepository.findAll();
    }

    public Moto getMotoById(int id){
        return motoRepository.findById(id).orElse(null);
    }

    public Moto save(Moto Moto){
        return motoRepository.save(Moto);
    }

    public List<Moto> getUsuarioById(int UsuarioId){
        return motoRepository.findByUsuarioId(UsuarioId);
    }
}
