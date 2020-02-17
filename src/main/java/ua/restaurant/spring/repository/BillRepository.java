package ua.restaurant.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.restaurant.spring.domain.Bill;
import ua.restaurant.spring.domain.User;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository <Bill, Long> {
    Page<Bill> findAllByOrder_User_UsernameOrderByInvoiceDateTimeDesc(String username, Pageable pageable);
    Page<Bill> findAllByOrder_User_IdOrderByOrder_DateDesc(Long id, Pageable pageable);
    Optional<Bill> findById(Long id);

}
