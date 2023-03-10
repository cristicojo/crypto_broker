package crypto.repository;

import crypto.entity.LimitOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitOrderRepository extends JpaRepository<LimitOrder, Long> {

}
