package com.example.hotel.repository;

import com.example.hotel.entity.CreditList;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CreditListRepository  extends JpaRepository<CreditList,Integer> {
    @Transactional
    List<CreditList> findAllByUserId(int userId);
}
