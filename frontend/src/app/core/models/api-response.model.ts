export interface ApiResponse<T> {
    status: number,
    message: string,
    data: T,
    errors?: Record<string, string>
}

export interface ErrorResponse {
    status: number,
    message: string,
    errors: Record<string, string>
}