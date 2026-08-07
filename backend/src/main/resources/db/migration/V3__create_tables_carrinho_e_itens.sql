CREATE TABLE carrinho (
                           id UUID PRIMARY KEY,
                           user_id UUID NOT NULL UNIQUE,
                           valor_total NUMERIC(19, 2) NOT NULL DEFAULT 0.00,
                           CONSTRAINT fk_carrinho_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE itens_carrinho (
                                id UUID PRIMARY KEY,
                                carrinho_id UUID NOT NULL,
                                produto_id UUID NOT NULL,
                                quantidade INT NOT NULL,
                                preco_unitario NUMERIC(19, 2) NOT NULL,
                                CONSTRAINT fk_item_carrinho FOREIGN KEY (carrinho_id) REFERENCES carrinho(id) ON DELETE CASCADE,
                                CONSTRAINT fk_item_produto FOREIGN KEY (produto_id) REFERENCES produto(id)
);