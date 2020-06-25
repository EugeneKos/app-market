package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.User;
import ru.market.domain.data.enumeration.UserStatus;

import java.time.LocalDateTime;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u join fetch u.person where u.username = :username")
    User findByUsername(@Param("username") String username);

    @Modifying
    @Query("update User u set u.status = :status, u.timestampStatus = :timestampStatus where u.username = :username")
    void updateUserStatusAndTimestampStatusByUsername(@Param("username") String username,
                                                      @Param("status") UserStatus status,
                                                      @Param("timestampStatus") LocalDateTime timestampStatus);

    @Modifying
    @Query("update User u set u.passwordAttemptCount = :passwordAttemptCount where u.username = :username")
    void updatePasswordAttemptCountByUsername(@Param("username") String username,
                                              @Param("passwordAttemptCount") Integer passwordAttemptCount);
}
