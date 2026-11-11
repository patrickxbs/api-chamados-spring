package br.edu.ufba.linguagens.chamados.controller;

import br.edu.ufba.linguagens.chamados.dto.ChamadoResponse;
import br.edu.ufba.linguagens.chamados.dto.NovoChamadoRequest;
import br.edu.ufba.linguagens.chamados.dto.ResumoChamadosResponse;
import br.edu.ufba.linguagens.chamados.model.Chamado;
import br.edu.ufba.linguagens.chamados.service.ChamadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {
    private final ChamadoService service;

    public ChamadoController(ChamadoService service) { this.service = service; }

    @GetMapping
    public List<ChamadoResponse> listarTodos() {
        return service.listarTodos().stream().map(ChamadoResponse::from).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable long id) {
        Chamado chamado = service.buscarPorId(id);
        return chamado == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Chamado não encontrado"))
                : ResponseEntity.ok(ChamadoResponse.from(chamado));
    }

    @GetMapping("/setor")
    public List<ChamadoResponse> buscarPorSetor(@RequestParam String nome) {
        return service.buscarPorSetor(nome).stream().map(ChamadoResponse::from).toList();
    }

    @GetMapping("/prioridade")
    public List<ChamadoResponse> buscarPorPrioridade(@RequestParam String nivel) {
        return service.buscarPorPrioridade(nivel).stream().map(ChamadoResponse::from).toList();
    }

    @GetMapping("/busca")
    public List<ChamadoResponse> buscarPorTitulo(@RequestParam String texto) {
        return service.buscarPorTitulo(texto).stream().map(ChamadoResponse::from).toList();
    }

    @GetMapping("/criticos")
    public List<ChamadoResponse> listarCriticos() {
        return service.listarCriticos().stream().map(ChamadoResponse::from).toList();
    }

    @GetMapping("/resumo")
    public ResumoChamadosResponse obterResumo() { return service.obterResumo(); }

    @PostMapping
    public ResponseEntity<ChamadoResponse> cadastrar(@RequestBody NovoChamadoRequest novo) {
        Chamado criado = service.cadastrar(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(ChamadoResponse.from(criado));
    }
}
