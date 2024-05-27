package security.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import security.instagram.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findAll();
    Optional<User> findById(Long id);
//    @Query(value = "Select u.id,u.user_name, u.email,u.create_at" +
//            "From user u " +
//            "WHERE u.create_at = " +
//            "(SELECT MAX(u2.create_at) FROM user u2)",
//            nativeQuery = true)
//    UserIdentity getLatestUserInfo();
}
