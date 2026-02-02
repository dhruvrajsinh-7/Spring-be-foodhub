package com.foodhub.backend.repository;

import com.foodhub.backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByActiveTrue();

}
