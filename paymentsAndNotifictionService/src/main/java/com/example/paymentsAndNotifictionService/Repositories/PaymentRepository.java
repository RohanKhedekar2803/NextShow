import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.paymentsAndNotifictionService.Entities.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

}
