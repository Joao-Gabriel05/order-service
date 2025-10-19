package store.order;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<OrderModel, String> {

    @EntityGraph(attributePaths = "items")
    List<OrderModel> findByIdAccountOrderByCreatedAtDesc(String idAccount);

    @EntityGraph(attributePaths = "items")
    Optional<OrderModel> findByIdAndIdAccount(String id, String idAccount);
}
