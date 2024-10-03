package auto_microservice.repository;

import auto_microservice.entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Integer> {
    List<Auto> findByUsuarioId(Integer usuarioId);
}
