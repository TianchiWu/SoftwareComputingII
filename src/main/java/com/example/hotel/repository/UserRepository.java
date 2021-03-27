package com.example.hotel.repository;

import com.example.hotel.entity.User;
import com.example.hotel.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Transactional
    List<User> findAllByUserType(UserType userType);
    @Transactional
    User findByEmail(String email);
    @Transactional
    List<User> findAllByEnterprisename(String enterprisename);

}
