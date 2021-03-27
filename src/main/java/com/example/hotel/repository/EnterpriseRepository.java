package com.example.hotel.repository;

import com.example.hotel.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {
    @Transactional
    void deleteByName(String name);
    @Transactional
    Enterprise findByName(String name);
}
