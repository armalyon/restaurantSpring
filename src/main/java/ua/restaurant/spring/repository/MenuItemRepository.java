package ua.restaurant.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ua.restaurant.spring.domain.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findByName(String itemName);
    List<MenuItem> findAll();
    List<MenuItem> findAllByStorageQuantityGreaterThan(Long greaterThan);

    @Modifying
    @Query(value = "UPDATE MenuItem m SET m.storageQuantity = (m.storageQuantity - :quantity) WHERE m.id = :id")
    void decreaseStorageQuantityById(Long id, Long quantity);
}
