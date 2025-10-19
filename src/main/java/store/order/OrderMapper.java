package store.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

final class OrderMapper {

    static ItemOut toOut(OrderItemModel m) {
        ProductSnapshot snap = ProductSnapshot.builder()
                .id(m.idProduct())
                .name(m.productName())
                .unit(m.productUnit())
                .price(m.productPrice())
                .build();

        return ItemOut.builder()
                .id(m.id())
                .product(snap)
                .quantity(m.quantity())
                .total(m.total())
                .build();
    }

    static OrderOut toOut(OrderModel o) {
        List<ItemOut> items = o.items().stream().map(OrderMapper::toOut).toList();
        return OrderOut.builder()
                .id(o.id())
                .createdAt(o.createdAt())
                .total(o.total())
                .items(items)
                .build();
    }

    public static OrderOut toOutSummary(OrderModel order) {
    return new OrderOut(
        order.id(),
        order.createdAt(),
        order.total(),
        null  
    );
    }
    static BigDecimal calcSubtotal(BigDecimal unitPrice, int qty) {
        return unitPrice.multiply(BigDecimal.valueOf(qty));
    }

    static OrderModel newOrder(String idAccount) {
        return new OrderModel()
                .createdAt(Instant.now())
                .idAccount(idAccount)
                .total(BigDecimal.ZERO);
    }
}

