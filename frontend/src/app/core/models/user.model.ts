export type UserRole = 'ADMIN' | 'CUSTOMER' | 'TENANT' | 'BARBER';

export type RegisterDTO = {
    name: string;
    email: string;
    password: string;
    whatsapp: string;
    role: UserRole;
}

export type LoginDTO = {
    email: string;
    password: string;
}

export type LoginResponseDTO = {
    token: string;
    email: string;
    role: UserRole;
    name: string;
}