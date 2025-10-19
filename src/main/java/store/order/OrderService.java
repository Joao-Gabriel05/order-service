package store.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import store.product.ProductController;
import store.product.ProductOut;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductController productClient; // Feign do módulo product

    public OrderOut create(OrderIn in, String idAccount) {
        if (idAccount == null || idAccount.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id-Account é obrigatório");
        }
        if (in == null || in.items() == null || in.items().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido sem itens");
        }

        OrderModel order = OrderMapper.newOrder(idAccount);

        in.items().forEach(ii -> {
            ProductOut p = fetchProduct(ii.idProduct());
            var total = OrderMapper.calcSubtotal(p.price(), ii.quantity());

            var item = new OrderItemModel()
                    .order(order)
                    .idProduct(p.id())
                    .productName(p.name())
                    .productUnit(p.unit())
                    .productPrice(p.price())
                    .quantity(ii.quantity())
                    .total(total);

            order.items().add(item);
            order.total(order.total().add(total));
        });

        return OrderMapper.toOut(orderRepository.save(order));
    }

    public List<OrderOut> findAll(String idAccount) {
        return orderRepository.findByIdAccountOrderByCreatedAtDesc(idAccount)
                .stream().map(OrderMapper::toOut).toList();
    }

    public OrderOut findById(String id, String idAccount) {
        var o = orderRepository.findByIdAndIdAccount(id, idAccount)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
        return OrderMapper.toOut(o);
    }
    /* ---------- helpers ---------- */
    private ProductOut fetchProduct(String productId) {
        try {
            var resp = productClient.findById(productId);
            var body = resp.getBody();
            if (body == null) throw new IllegalStateException("Produto não encontrado");
            return body;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto inválido: " + productId);
        }
    }
}
