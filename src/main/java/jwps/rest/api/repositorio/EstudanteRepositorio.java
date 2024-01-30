package jwps.rest.api.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jwps.rest.api.modelo.Estudante;

@Repository
public interface EstudanteRepositorio extends JpaRepository<Estudante, Long> {

}
