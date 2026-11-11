package br.edu.ufba.linguagens.chamados.dto;

import br.edu.ufba.linguagens.chamados.model.Chamado;

public record ChamadoResponse(
        Long id,
        String titulo,
        String solicitante,
        String setor,
        Chamado.Prioridade prioridade,
        Chamado.Status status) {

    public static ChamadoResponse from(Chamado chamado) {
        return new ChamadoResponse(chamado.id(), chamado.titulo(), chamado.solicitante(),
                chamado.setor(), chamado.prioridade(), chamado.status());
    }
}
