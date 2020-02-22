package ua.restaurant.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.Bill;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.domain.type.BillStatement;
import ua.restaurant.spring.exception.IdNotFoundException;
import ua.restaurant.spring.repository.BillRepository;
import ua.restaurant.spring.repository.OrderRepository;

import java.time.LocalDateTime;

import static ua.restaurant.spring.domain.type.OrderStatement.INVOICED;
import static ua.restaurant.spring.domain.type.OrderStatement.REJECTED;

@Slf4j
@Service
public class AdminBillService {
    private BillRepository billRepository;
    private OrderRepository orderRepository;

    @Autowired
    public AdminBillService(BillRepository billRepository, OrderRepository orderRepository) {
        this.billRepository = billRepository;
        this.orderRepository = orderRepository;
    }

    public boolean saveNewBill(Long orderId) throws IdNotFoundException {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new IdNotFoundException("Not found order id", orderId));
        if (order.getOrderStatement()
                .equals(INVOICED) || order.getOrderStatement().equals(REJECTED)) {
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
