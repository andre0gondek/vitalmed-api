package distribuidora_api.domain.enums;

public enum Cargo {
    // Acesso total: Pode gerenciar usuários, deletar registros (se permitido) e configurar o sistema.
    ADMINISTRADOR,

    // Gestão operacional: Pode cadastrar/editar novos insumos, categorias e alterar níveis de estoque mínimo.
    GERENTE,

    // Operação diária: Foco exclusivo em registrar Entradas e Saídas e visualizar o estoque. Não pode deletar insumos.
    ALMOXARIFE,

    // Acesso "Somente Leitura" (Read-Only): Essencial para o requisito de "auditorias de saúde".
    // Pode ver todo o histórico de movimentação, mas não pode alterar nada.
    AUDITOR
}
