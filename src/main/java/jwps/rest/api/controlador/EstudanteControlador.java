package jwps.rest.api.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jwps.rest.api.dto.EstudanteCreateDTO;
import jwps.rest.api.dto.EstudanteResponseDTO;
import jwps.rest.api.dto.mapper.EstudanteMapper;
import jwps.rest.api.excecao.EstudanteNotFoundException;
import jwps.rest.api.modelo.Estudante;
import jwps.rest.api.servico.EstudanteServico;

@RestController
@RequestMapping("/estudantes")
public class EstudanteControlador {

	@Autowired
	private EstudanteServico estudanteServico;

	@Autowired
	private EstudanteMapper estudanteMapper;

	@PostMapping
	public ResponseEntity<EstudanteResponseDTO> salvar(@RequestBody EstudanteCreateDTO estudanteCreateDTO) {

		Estudante estudante = estudanteMapper.toEntity(estudanteCreateDTO);
		Estudante estudanteSalvo = estudanteServico.salvarEstudante(estudante);
		EstudanteResponseDTO estudanteResponseDTO = estudanteMapper.toDTO(estudanteSalvo);

		return ResponseEntity.status(HttpStatus.CREATED).body(estudanteResponseDTO);
	}

	@GetMapping
	public ResponseEntity<List<EstudanteResponseDTO>> listarEstudantes() {

		List<Estudante> estudantes = estudanteServico.listarEstudantes();
		List<EstudanteResponseDTO> estudantesResponseDTO = estudanteMapper.toDTO(estudantes);

		return ResponseEntity.status(HttpStatus.OK).body(estudantesResponseDTO);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarEstudantePorId(@PathVariable(value = "id") Long id) {
		try {
			Estudante estudanteSalvo = estudanteServico.buscarEstudantePorId(id);
			EstudanteResponseDTO estudanteResponseDTO = estudanteMapper.toDTO(estudanteSalvo);
			return ResponseEntity.status(HttpStatus.OK).body(estudanteResponseDTO);
		} catch (EstudanteNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> alterar(@PathVariable(value = "id") Long id,
			@RequestBody EstudanteCreateDTO estudanteCreateDTO) {
		try {
			Estudante estudante = estudanteMapper.toEntity(estudanteCreateDTO);
			Estudante estudanteGravado = estudanteServico.alterarEstudante(id, estudante);
			EstudanteResponseDTO estudanteResponseDTO = estudanteMapper.toDTO(estudanteGravado);

			return ResponseEntity.status(HttpStatus.OK).body(estudanteResponseDTO);

		} catch (EstudanteNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> apagar(@PathVariable(value = "id") Long id) {
		try {
			estudanteServico.apagarEstudante(id);
			
			return ResponseEntity.status(HttpStatus.OK).body("Removido com sucesso.");
			
		} catch (EstudanteNotFoundException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

}
