package ua.restaurant.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.Bill;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.exception.IdNotFoundException;
import ua.restaurant.spring.exception.NotEnoughFundsException;
import ua.restaurant.spring.exception.UserNotFoundException;
import ua.restaurant.spring.repository.BillRepository;
import ua.restaurant.spring.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static ua.restaurant.spring.domain.type.BillStatement.PAYED;
import static ua.restaurant.spring.service.utility.Constants.ADMIN_USERNAME;

@Slf4j
@Service
public class PayBillService {
    private BillRepository billRepository;
    private UserRepository userRepository;

    @Autowired
    public PayBillService(BillRepository billRepository, UserRepository userRepository) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
    }

    public boolean payBill(Long id, String username) throws NotEnoughFundsException, IdNotFoundException, UserNotFoundException {
        Bill bill = getBillById(id);
        User user = getUserByUsername(username);
        User admin = getUserByUsername(ADMIN_USERNAME);
        long transactionSum = bill.getOrder().getTotalPrice();
        if (isBillNotPayed(bill) && isFundsEnough(bill, user)) {
            prepareEntitiesToPaymentTransaction(user, admin, bill, transactionSum);
            savePaymentEntities(bill, user, admin);
            return true;
        }
        return false;
    }

    private void prepareEntitiesToPaymentTransaction(User user,
                                                     User admin,
                                                     Bill bill,
                                                     long transactionSum) {
        bill.setStatement(PAYED);
        bill.setPaymentDateTime(LocalDateTime.now());
        user.setFunds(user.getFunds() - transactionSum);
        admin.setFunds(admin.getFunds() + transactionSum);
    }

    @Transactional
    public void savePaymentEntities(Bill bill, User user, User admin) {
        userRepository.save(user);
        userRepository.save(admin);
        billRepository.save(bill);

    }


    private boolean isFundsEnough(Bill bill, User user) throws NotEnoughFundsException {
        long invoice = bill.getOrder().getTotalPrice();
        long funds = user.getFunds();
        if (invoice > funds) throw new NotEnoughFundsException("not enough funds", invoice - funds, bill.getId());
        return true;
    }

    private boolean isBillNotPayed(Bill bill) {
        return !bill.getStatement().equals(PAYED);
    }

    private Bill getBillById(Long id) throws IdNotFoundException {
        return billRepository.findById(id)
                .orElseThrow(
                        () -> new IdNotFoundException("bill id not found in DB", id)
                );
    }

    private User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UserNotFoundException("user not found", username)
                );
    }
}
