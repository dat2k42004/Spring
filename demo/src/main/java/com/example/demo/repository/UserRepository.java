package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.domain.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     User save(User user);

     List<User> findByEmail(String email);

     User findOneById(long id);

     boolean existsByEmail(String email);

     User findOneByEmail(String email);
}
