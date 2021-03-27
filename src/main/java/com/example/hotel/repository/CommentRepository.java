package com.example.hotel.repository;

import com.example.hotel.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @Transactional
    List<Comment> findAllByHotelId(int hotelId);
}
