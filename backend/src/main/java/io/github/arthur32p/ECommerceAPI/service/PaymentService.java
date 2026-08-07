package io.github.arthur32p.ECommerceAPI.service;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import io.github.arthur32p.ECommerceAPI.model.ItemPedido;
import io.github.arthur32p.ECommerceAPI.model.Pedido;
import io.github.arthur32p.ECommerceAPI.model.StatusPedido;
import io.github.arthur32p.ECommerceAPI.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PedidoRepository pedidoRepository;

    @Value("${stripe.success-url}")
    private String successUrl;

    @Value("${stripe.cancel-url}")
    private String cancelUrl;

    @Value("${stripe.webhook-secret}")
    private String endpointSecret;

    public Session criarSessaoCheckout(Pedido pedido) throws StripeException {

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        for(ItemPedido item: pedido.getItens()){
            long preco = item.getPrecoUnitario()
                    .multiply(new BigDecimal("100"))
                    .longValue();
            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity((long) item.getQuantidade())
                    .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("brl")
                                    .setUnitAmount(preco)
                                    .setProductData(
                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                    .setName(item.getProduto().getNome())
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            lineItems.add(lineItem);
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(this.successUrl)
                .setCancelUrl(this.cancelUrl)
                .addAllLineItem(lineItems)
                .putMetadata("pedidoId", pedido.getId().toString())
                .build();

        return Session.create(params);

    }

    @Transactional
    public void processarWebhookStripe(String payload, String sigHeader) throws SignatureVerificationException {
        Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

        if ("checkout.session.completed".equals(event.getType())) {

            Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);

            if (session == null) {
                session = (Session) event.getData().getObject();
            }

            if (session != null) {
                String sessionId = session.getId();

                Pedido pedido = pedidoRepository.findByStripePaymentIntentId(sessionId)
                        .orElseThrow(() -> new EntityNotFoundException("Pedido não localizado para o ID de sessão: " + sessionId));

                if (pedido.getStatus() == StatusPedido.PENDENTE) {
                    pedido.setStatus(StatusPedido.PAGO);
                    pedidoRepository.save(pedido);
                }
            }
        }
    }

}
