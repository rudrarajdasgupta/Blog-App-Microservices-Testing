package com.microservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
