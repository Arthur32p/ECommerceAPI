CREATE TABLE pedidos (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         user_id UUID NOT NULL,
                         status VARCHAR(50) NOT NULL,
                         valor_total NUMERIC(19, 2) NOT NULL,
                         data_criacao TIMESTAMP NOT NULL,
                         stripe_payment_intent_id VARCHAR(255),
                         CONSTRAINT fk_pedidos_user FOREIGN KEY (user_id) REFERENCES users(id),
                         CONSTRAINT uk_pedidos_stripe_payment_intent_id UNIQUE (stripe_payment_intent_id)
);

CREATE TABLE itens_pedido (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              pedido_id UUID NOT NULL,
                              produto_id UUID NOT NULL,
                              quantidade INTEGER NOT NULL,
                              preco_unitario NUMERIC(19, 2) NOT NULL,
                              CONSTRAINT fk_itens_pedido_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
                              CONSTRAINT fk_itens_pedido_produto FOREIGN KEY (produto_id) REFERENCES produto(id)
);