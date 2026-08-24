
CREATE TABLE tenants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    owner_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    company_name VARCHAR(150) NOT NULL,
    opening_hours TIME NOT NULL,
    closing_hours TIME NOT NULL,
    slug VARCHAR(100) NOT NULL UNIQUE,
    whatsapp VARCHAR(20) NOT NULL,
    company_address VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);