package com.Demo.taskManagement.repository;

import com.Demo.taskManagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
