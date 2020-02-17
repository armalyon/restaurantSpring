package ua.restaurant.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.Bill;
import ua.restaurant.spring.dto.BillsDTO;
import ua.restaurant.spring.repository.BillRepository;


@Service
public class ClientBillsService {
    private BillRepository billRepository;

    @Autowired
    public ClientBillsService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Page<Bill> getBillsByUserNameNewestFirst(String username, Pageable pageable) {
        return billRepository
                        .findAllByOrder_User_UsernameOrderByInvoiceDateTimeDesc(username, pageable);
    }

    public Page<Bill> getBillsByUserIdNewestFirst(Long id, Pageable pageable) {
        return billRepository
                        .findAllByOrder_User_IdOrderByOrder_DateDesc(id, pageable);
    }
}
