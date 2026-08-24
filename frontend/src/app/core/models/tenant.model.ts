export type RegisterTenantDTO = {
    companyName: string;
    openingHours: string;
    closingHours: string;
    whatsapp: string;
    companyAddress: string;
    slug: string;
}

export type TenantResponseDTO = {
    id: string;
    companyName: string;
    openingHours: string;
    closingHours: string;
    whatsapp: string;
    companyAddress: string;
    slug: string;
}