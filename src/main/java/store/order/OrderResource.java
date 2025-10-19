package store.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderResource implements OrderController {

    private final OrderService service;

    @Override
    public OrderOut create(OrderIn orderIn, String idAccount) {
        return service.create(orderIn, idAccount);
    }

    @Override
    public List<OrderOut> findAll(String idAccount) {
        return service.findAll(idAccount);
    }

    @Override
    public OrderOut findById(String id, String idAccount) {
        return service.findById(id, idAccount);
    }

}
