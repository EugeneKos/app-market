package ru.market.domain.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.User;
import ru.market.domain.data.enumeration.UserStatus;

import java.time.LocalDateTime;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.username = :username")
    User findByUsername(@Param("username") String username);

    @Modifying
    @Query("update User u set u.status = :status, u.timestampStatus = :timestampStatus where u.id = :id")
    void updateUserStatusAndTimestampStatusById(@Param("id") Long id, @Param("status") UserStatus status,
                                                @Param("timestampStatus") LocalDateTime timestampStatus);
}
