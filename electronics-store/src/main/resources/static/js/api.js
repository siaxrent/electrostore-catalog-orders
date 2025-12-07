const API_BASE = '';
let CURRENT_LANG = 'ru';

function getToastContainer() {
    let c = document.getElementById('toast-root');
    if (!c) {
        c = document.createElement('div');
        c.id = 'toast-root';
        c.className = 'toast-container';
        document.body.appendChild(c);
    }
    return c;
}

function showToast(message, type = 'info') {
    const container = getToastContainer();
    const el = document.createElement('div');
    el.className = 'toast ' + (type === 'success' ? 'toast-success' : type === 'error' ? 'toast-error' : '');
    const icon = document.createElement('span');
    icon.className = 'toast-icon';
    icon.textContent = type === 'success' ? '✔' : type === 'error' ? '⚠' : 'ℹ';
    const text = document.createElement('span');
    text.textContent = message;
    el.appendChild(icon);
    el.appendChild(text);
    container.appendChild(el);
    setTimeout(() => {
        el.style.opacity = '0';
        setTimeout(() => el.remove(), 250);
    }, 3000);
}

function getAuthToken() {
    try {
        return window.localStorage.getItem('auth_basic') || null;
    } catch {
        return null;
    }
}

function setAuthToken(username, password) {
    const token = 'Basic ' + btoa(`${username}:${password}`);
    window.localStorage.setItem('auth_basic', token);
}

function clearAuthToken() {
    window.localStorage.removeItem('auth_basic');
}

async function apiRequest(path, options = {}) {
    const resp = await fetch(API_BASE + path, options);
    const text = await resp.text();
    let data;
    try {
        data = text ? JSON.parse(text) : null;
    } catch {
        data = text;
    }
    if (!resp.ok) {
        if (resp.status === 401 && !window.location.pathname.includes('login.html')) {
            clearAuthToken();
            showToast(CURRENT_LANG === 'ru' ? 'Требуется авторизация' : 'Authorization required', 'error');
            setTimeout(() => window.location.href = '/login.html', 500);
        }
        const msg = typeof data === 'string'
            ? data
            : (data && data.message) || (CURRENT_LANG === 'ru' ? `Ошибка ${resp.status}` : `Error ${resp.status}`);
        throw new Error(msg);
    }
    return data;
}

function authHeaders(json = true) {
    const h = {};
    const token = getAuthToken();
    if (token) {
        h['Authorization'] = token;
    }
    if (json) {
        h['Content-Type'] = 'application/json';
    }
    return h;
}

async function apiGet(path) {
    return apiRequest(path);
}

async function apiPost(path, body) {
    return apiRequest(path, {
        method: 'POST',
        headers: authHeaders(true),
        body: JSON.stringify(body),
    });
}

async function apiDelete(path) {
    return apiRequest(path, {
        method: 'DELETE',
        headers: authHeaders(false),
    });
}

async function apiPatch(path, body) {
    return apiRequest(path, {
        method: 'PATCH',
        headers: authHeaders(true),
        body: JSON.stringify(body),
    });
}

function formatMoney(value) {
    if (value == null) return '-';
    const num = typeof value === 'number' ? value : Number(value);
    if (Number.isNaN(num)) return String(value);
    const locale = CURRENT_LANG === 'ru' ? 'ru-RU' : 'en-US';
    return num.toLocaleString(locale, { minimumFractionDigits: 2, maximumFractionDigits: 2 });
}

function initLanguageToggle(applyLangFn) {
    const btn = document.getElementById('langToggle');
    if (!btn) {
        applyLangFn && applyLangFn(CURRENT_LANG);
        return;
    }
    const updateLabel = () => {
        btn.textContent = CURRENT_LANG === 'ru' ? 'RU' : 'EN';
    };
    btn.addEventListener('click', () => {
        CURRENT_LANG = CURRENT_LANG === 'ru' ? 'en' : 'ru';
        document.documentElement.lang = CURRENT_LANG;
        updateLabel();
        applyLangFn && applyLangFn(CURRENT_LANG);
    });
    updateLabel();
    applyLangFn && applyLangFn(CURRENT_LANG);
}

