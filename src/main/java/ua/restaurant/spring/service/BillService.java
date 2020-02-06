package ua.restaurant.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.Bill;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.domain.types.BillStatement;
import ua.restaurant.spring.exceptions.IdNotFoundExeption;
import ua.restaurant.spring.repository.BillRepository;
import ua.restaurant.spring.repository.OrderRepository;

import java.time.LocalDateTime;

import static ua.restaurant.spring.domain.types.OrderStatement.INVOICED;

@Slf4j
@Service
public class BillService {
    private BillRepository billRepository;
    private OrderRepository orderRepository;

    @Autowired
    public BillService(BillRepository billRepository, OrderRepository orderRepository) {
        this.billRepository = billRepository;
        this.orderRepository = orderRepository;
    }

    public boolean saveNewBill(Long orderId) throws IdNotFoundExeption {
        Order order =
                orderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new IdNotFoundExeption("Not found order id", orderId));
        if (order.getOrderStatement()
                .equals(
                        INVOICED)
        ) {
            log.warn("Order statement mismatch. ID:" + orderId);
            return false;
        }
        saveInvoicedOrder(order);
        return true;
    }

    private void saveInvoicedOrder(Order order) {
        order.setOrderStatement(INVOICED);
        try {
            billRepository.save(
                    createNewBill(order)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bill createNewBill(Order order) {
        return Bill.builder()
                .invoiceDateTime(
                        LocalDateTime.now()
                )
                .order(order)
                .statement(BillStatement.INVOICE)
                .build();
    }

}
