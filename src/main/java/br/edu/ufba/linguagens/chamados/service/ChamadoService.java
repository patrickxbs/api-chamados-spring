package br.edu.ufba.linguagens.chamados.service;

import br.edu.ufba.linguagens.chamados.model.Chamado;
import br.edu.ufba.linguagens.chamados.dto.NovoChamadoRequest;
import br.edu.ufba.linguagens.chamados.dto.ResumoChamadosResponse;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

@Service
public class ChamadoService {
    private final List<Chamado> chamados = new ArrayList<>();
    private long proximoId = 11;

    public ChamadoService() {
        adicionarInicial(1, "Computador não liga", "Ana Souza", "Financeiro", Chamado.Prioridade.ALTA, Chamado.Status.ABERTO);
        adicionarInicial(2, "Impressora sem conexão", "Bruno Lima", "Recursos Humanos", Chamado.Prioridade.MEDIA, Chamado.Status.EM_ANDAMENTO);
        adicionarInicial(3, "Acesso ao sistema bloqueado", "Carla Reis", "Financeiro", Chamado.Prioridade.ALTA, Chamado.Status.EM_ANDAMENTO);
        adicionarInicial(4, "Instalar editor de texto", "Diego Alves", "Marketing", Chamado.Prioridade.BAIXA, Chamado.Status.RESOLVIDO);
        adicionarInicial(5, "Rede instável no escritório", "Elisa Costa", "Comercial", Chamado.Prioridade.ALTA, Chamado.Status.ABERTO);
        adicionarInicial(6, "Configurar e-mail corporativo", "Felipe Rocha", "Operações", Chamado.Prioridade.MEDIA, Chamado.Status.RESOLVIDO);
        adicionarInicial(7, "Teclado com teclas defeituosas", "Giovana Melo", "Marketing", Chamado.Prioridade.BAIXA, Chamado.Status.ABERTO);
        adicionarInicial(8, "VPN não conecta", "Heitor Nunes", "Tecnologia", Chamado.Prioridade.ALTA, Chamado.Status.RESOLVIDO);
        adicionarInicial(9, "Monitor piscando", "Isabela Dias", "Financeiro", Chamado.Prioridade.MEDIA, Chamado.Status.EM_ANDAMENTO);
        adicionarInicial(10, "Solicitar acesso à pasta compartilhada", "João Pedro", "Recursos Humanos", Chamado.Prioridade.BAIXA, Chamado.Status.ABERTO);
    }

    private void adicionarInicial(long id, String titulo, String solicitante, String setor,
                                 Chamado.Prioridade prioridade, Chamado.Status status) {
        chamados.add(new Chamado(id, titulo, solicitante, setor, prioridade, status));
    }

    public List<Chamado> listarTodos() {
        return chamados.stream().sorted(Comparator.comparing(Chamado::id)).toList();
    }

    public Chamado buscarPorId(long id) {
        return chamados.stream().filter(chamado -> chamado.id() == id).findFirst().orElse(null);
    }

    public List<Chamado> buscarPorSetor(String nome) {
        return chamados.stream().filter(c -> c.setor().toLowerCase().contains(nome.toLowerCase()))
                .sorted(Comparator.comparing(Chamado::id)).toList();
    }

    public List<Chamado> buscarPorPrioridade(String nivel) {
        Chamado.Prioridade prioridade = Chamado.Prioridade.valueOf(nivel.toUpperCase());
        return chamados.stream().filter(c -> c.prioridade() == prioridade)
                .sorted(Comparator.comparing(Chamado::id)).toList();
    }

    public List<Chamado> buscarPorTitulo(String texto) {
        return chamados.stream().filter(c -> c.titulo().toLowerCase().contains(texto.toLowerCase()))
                .sorted(Comparator.comparing(Chamado::id)).toList();
    }

    public List<Chamado> listarCriticos() {
        return chamados.stream().filter(c -> c.prioridade() == Chamado.Prioridade.ALTA
                        && c.status() != Chamado.Status.RESOLVIDO)
                .sorted(Comparator.comparing(Chamado::id)).toList();
    }

    public ResumoChamadosResponse obterResumo() {
        long abertos = contar(Chamado.Status.ABERTO);
        long emAndamento = contar(Chamado.Status.EM_ANDAMENTO);
        long resolvidos = contar(Chamado.Status.RESOLVIDO);
        return new ResumoChamadosResponse(abertos, emAndamento, resolvidos, chamados.size());
    }

    private long contar(Chamado.Status status) {
        return chamados.stream().filter(c -> c.status() == status).count();
    }

    public Chamado cadastrar(NovoChamadoRequest novo) {
        long id = proximoId++;
        Chamado chamado = new Chamado(id, novo.titulo(), novo.solicitante(), novo.setor(), novo.prioridade(), novo.status());
        chamados.add(chamado);
        return chamado;
    }
}
