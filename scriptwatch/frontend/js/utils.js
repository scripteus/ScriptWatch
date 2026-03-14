/**
 * Utilitários do ScriptWatch
 */

/** Formata duração em ms para leitura humana */
function formatDuration(ms) {
    if (ms == null || ms === undefined) return '-';
    if (ms < 1000) return `${ms}ms`;
    if (ms < 60000) return `${(ms / 1000).toFixed(2)}s`;
    const min = Math.floor(ms / 60000);
    const sec = ((ms % 60000) / 1000).toFixed(0);
    return `${min}m ${sec}s`;
}

/** Formata data/hora para exibição */
function formatDateTime(dt) {
    if (!dt) return '-';
    const d = new Date(dt);
    return d.toLocaleString('pt-BR');
}

/** Retorna classe CSS para badge de status */
function statusClass(status) {
    return status === 'SUCESSO' 
        ? 'bg-emerald-100 text-emerald-800' 
        : 'bg-red-100 text-red-800';
}
