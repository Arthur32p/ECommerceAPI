CREATE TABLE produto (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    preco DECIMAL(6, 2) NOT NULL,
    quantidade_estoque INTEGER NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);