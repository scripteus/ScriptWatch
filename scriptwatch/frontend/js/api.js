/**
 * ScriptWatch - Cliente API
 * Configuração base e funções para consumo da API REST
 */

const API_BASE = 'http://localhost:8080/api';

/**
 * Requisição GET genérica
 */
async function apiGet(endpoint) {
    const res = await fetch(`${API_BASE}${endpoint}`);
    if (!res.ok) throw new Error(`Erro: ${res.status}`);
    return res.json();
}

/**
 * Requisição POST genérica
 */
async function apiPost(endpoint, body) {
    const res = await fetch(`${API_BASE}${endpoint}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    });
    if (!res.ok) {
        const err = await res.text();
        throw new Error(err || `Erro: ${res.status}`);
    }
    return res.json();
}

/**
 * Requisição PUT genérica
 */
async function apiPut(endpoint, body) {
    const res = await fetch(`${API_BASE}${endpoint}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    });
    if (!res.ok) throw new Error(`Erro: ${res.status}`);
    return res.json();
}

/**
 * Requisição DELETE genérica
 */
async function apiDelete(endpoint) {
    const res = await fetch(`${API_BASE}${endpoint}`, { method: 'DELETE' });
    if (!res.ok && res.status !== 204) throw new Error(`Erro: ${res.status}`);
    return res.status === 204 ? null : res.json();
}

// Helpers específicos
const api = {
    scripts: {
        list: () => apiGet('/scripts'),
        get: (id) => apiGet(`/scripts/${id}`),
        create: (data) => apiPost('/scripts', data),
        update: (id, data) => apiPut(`/scripts/${id}`, data),
        delete: (id) => apiDelete(`/scripts/${id}`)
    },
    executions: {
        list: (limit = 100) => apiGet(`/executions?limit=${limit}`),
        get: (id) => apiGet(`/executions/${id}`),
        listByScript: (scriptId) => apiGet(`/executions/script/${scriptId}`),
        create: (data) => apiPost('/executions', data)
    },
    dashboard: {
        stats: () => apiGet('/dashboard/stats'),
        monitoramento: () => apiGet('/dashboard/monitoramento')
    }
};
