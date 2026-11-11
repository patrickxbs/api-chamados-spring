package br.edu.ufba.linguagens.chamados.model;

public record Chamado(
        Long id,
        String titulo,
        String solicitante,
        String setor,
        Prioridade prioridade,
        Status status) {

    public enum Prioridade { BAIXA, MEDIA, ALTA }
    public enum Status { ABERTO, EM_ANDAMENTO, RESOLVIDO }
}
