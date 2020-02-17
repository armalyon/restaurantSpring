package ua.restaurant.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.domain.type.OrderStatement;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser_UsernameOrderByDateDesc(String username);

    Page<Order> findAllByOrderStatementOrderByDate(OrderStatement statement, Pageable pageable);

    Integer countByUser_Id(Long id);

    Optional<Order> findById(Long id);

    List<Order> findAllByUser_UsernameAndDateOrderByTimeDesc(String username, LocalDate date);


    @Modifying
    @Query(value = "UPDATE Order o SET o.orderStatement = :statement WHERE o.id = :orderId")
    void updateOrderStatementById(@Param("statement") OrderStatement statement, @Param("orderId") Long orderId);

}
