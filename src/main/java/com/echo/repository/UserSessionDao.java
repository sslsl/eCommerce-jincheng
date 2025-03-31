package com.echo.repository;

import com.echo.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionDao extends JpaRepository<UserSession, Integer> {
    Optional<UserSession> findByToken(String token);

    Optional<UserSession> findByUserId(Integer userId);

}
