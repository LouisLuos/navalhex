export type TreatmentResponseDTO = {
    id: string;
    title: string;
    description: string;
    price: number;
    durationMinutes: number;
}

export type RegisterTreatmentDTO = {
    title: string;
    description: string;
    price: number;
    durationMinutes: number;
}

export type UpdateTreatmentDTO = {
    id: string;
    title?: string;
    description?: string;
    price?: number;
    durationMinutes?: number;
}