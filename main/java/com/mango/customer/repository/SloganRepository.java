package com.mango.customer.repository;

import com.mango.customer.model.Slogan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SloganRepository extends JpaRepository<Slogan, Long> {
	List<Slogan> findByUserId(Long userId);
}
