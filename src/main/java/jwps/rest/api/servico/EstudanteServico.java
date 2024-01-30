package jwps.rest.api.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jwps.rest.api.excecao.EstudanteNotFoundException;
import jwps.rest.api.modelo.Estudante;
import jwps.rest.api.repositorio.EstudanteRepositorio;

@Service
public class EstudanteServico {

	@Autowired
	private EstudanteRepositorio estudanteRepositorio;

	public Estudante salvarEstudante(Estudante estudante) {
		return estudanteRepositorio.save(estudante);
	}

	public List<Estudante> listarEstudantes() {
		return estudanteRepositorio.findAll();
	}

	public Estudante buscarEstudantePorId(Long id) throws EstudanteNotFoundException {
		Optional<Estudante> estudanteEncontrado = estudanteRepositorio.findById(id);

		if (estudanteEncontrado.isPresent()) {
			return estudanteEncontrado.get();
		} else {
			throw new EstudanteNotFoundException("Estudante com o id '" + id + "' não foi encontrado.");
		}
	}

	public Estudante alterarEstudante(Long id, Estudante estudante) throws EstudanteNotFoundException {
		Estudante estudanteGravadoNoBD = buscarEstudantePorId(id);
		estudanteGravadoNoBD.setIdade(estudante.getIdade());
		estudanteGravadoNoBD.setNome(estudante.getNome());

		Estudante estudanteAtualizado = estudanteGravadoNoBD;

		return estudanteRepositorio.save(estudanteAtualizado);
	}

	public void apagarEstudante(Long id) throws EstudanteNotFoundException {
		Estudante estudanteGravadoNoBD = buscarEstudantePorId(id);
		estudanteRepositorio.delete(estudanteGravadoNoBD);
	}

}
