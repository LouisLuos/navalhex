export type UserRole = 'ADMIN' | 'CUSTOMER' | 'TENANT' | 'BARBER';

export type RegisterDTO = {
    name: string;
    email: string;
    password: string;
    whatsapp: string;
    role: UserRole;
}