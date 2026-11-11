package br.edu.ufba.linguagens.chamados.dto;

import br.edu.ufba.linguagens.chamados.model.Chamado;

public record NovoChamadoRequest(
        String titulo,
        String solicitante,
        String setor,
        Chamado.Prioridade prioridade,
        Chamado.Status status) {
}
