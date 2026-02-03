package com.foodhub.backend.repository;

import com.foodhub.backend.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

}
