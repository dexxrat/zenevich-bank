import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Добавляем токен к каждому запросу
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export interface RegisterRequest {
  email: string;
  password: string;
  fullName: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  userId: string;
  email: string;
  fullName: string;
}

export interface AccountResponse {
  id: string;
  accountNumber: string;
  balance: number;
  currency: string;
  createdAt: string;
}

export interface CreateAccountRequest {
  currency: string;
}

export const authApi = {
  register: (data: RegisterRequest) => api.post<AuthResponse>('/auth/register', data),
  login: (data: LoginRequest) => api.post<AuthResponse>('/auth/login', data),
  getMe: () => api.get<AuthResponse>('/auth/me'),
};

export const accountApi = {
  createAccount: (data: CreateAccountRequest) => api.post<AccountResponse>('/accounts', data),
  getAccounts: () => api.get<AccountResponse[]>('/accounts'),
  getAccountById: (id: string) => api.get<AccountResponse>(`/accounts/${id}`),
};

export default api;