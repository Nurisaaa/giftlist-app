package com.example.giftlistb8.repositories;


import com.example.giftlistb8.dto.profile.response.ProfileResponse;
import com.example.giftlistb8.dto.profile.response.ProfileResponseGetById;
import com.example.giftlistb8.dto.user.response.UserResponseGetAll;
import com.example.giftlistb8.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select new com.example.giftlistb8.dto.profile.response.ProfileResponseGetById(u.id,ui.image,concat(u.firstName,' ',u.lastName),u.email) from User u join u.userInfo ui where u.id=:userId")
    Optional<ProfileResponseGetById> getByIdUser(Long userId);

    @Query("select new com.example.giftlistb8.dto.profile.response.ProfileResponse(u.id,ui.image,u.lastName,u.firstName,ui.country,u.email,ui.hobby,ui.dateOfBirth,ui.phoneNumber,ui.important," +
            "ui.clothingSize,ui.shoeSize,ui.facebook ,ui.instagram,ui.telegram,ui.whatsApp) from User u join u.userInfo ui where u.id=:userId")
    Optional<ProfileResponse> getByIdUserDetail(Long userId);

    Optional<User> getUserById(Long userId);

    @Query("SELECT COUNT(u.id) > 0 FROM User u " +
           "JOIN u.friends f where u.id = ?1 and f.id = ?2")
    boolean inMyFriends(Long currentUserId,Long userId);
    @Query("SELECT count (u.id) > 0 FROM User u " +
           "JOIN u.requestsForFriends r where u.id = ?1 and r.id = ?2")
    boolean inMyRequests(Long currentUser,Long userId);

    @Modifying
    @Query(nativeQuery = true,value = "DELETE FROM users u WHERE u.id = ?1")
    void deleteUser(Long userId);

    @Modifying
    @Query(nativeQuery = true,value = "DELETE FROM reserves r WHERE r.user_id = ?1")
    void deleteFromReserve(Long userId);

    @Modifying
    @Query(nativeQuery = true,value = "DELETE FROM users_requests_for_friends  WHERE requests_for_friends_id = ?1")
    void deleteFromUsersRequestsForFriends(Long userId);

    @Modifying
    @Query(nativeQuery = true,value = "DELETE FROM notifications WHERE from_whom_user_id = ?1 OR to_whom_user_id = ?1")
    void deleteFromNotifications(Long userId);

    @Modifying
    @Query(nativeQuery = true,value = "DELETE FROM users_friends WHERE user_id = ?1 OR friends_id = ?1")
    void deleteFromFriends(Long userId);

    @Modifying
    @Query(nativeQuery = true,value = "DELETE FROM wishes WHERE user_id = ?1")
    void deleteFromWish(Long userId);

    @Modifying
    @Query(nativeQuery = true,value = "DELETE FROM holidays WHERE user_id = ?1")
    void deleteFromHoliday(Long userId);

    @Query("SELECT NEW com.example.giftlistb8.dto.user.response.UserResponseGetAll" +
            "(u.id, ui.image, CONCAT(u.lastName, ' ', u.firstName), CAST(count(w.id) AS int), u.isBlocked) " +
            "FROM User u " +
            "LEFT JOIN u.userInfo ui " +
            "LEFT JOIN u.wishes w " +
            "WHERE u.id NOT IN (select u.id from User where u.role = 'ADMIN') AND " +
            "  (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) and u.role <> 'ADMIN') OR " +
            "  (LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) and u.role <> 'ADMIN') OR " +
            "  (LOWER(u.userInfo.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) and u.role <> 'ADMIN') OR " +
            "  (LOWER(u.userInfo.country) LIKE LOWER(CONCAT('%', :keyword, '%')) and u.role <> 'ADMIN') " +
            "GROUP BY u.id, ui.image, u.lastName, u.firstName, u.isBlocked " +
            "ORDER BY u.id DESC")
    List<UserResponseGetAll> globalSearch(@Param("keyword") String keyword);

    @Query("SELECT NEW com.example.giftlistb8.dto.user.response.UserResponseGetAll" +
            "(f.id, fu.image, CONCAT(f.lastName, ' ', f.firstName), CAST(count(fw.id) AS int), f.isBlocked) " +
            "FROM User u " +
            "LEFT JOIN u.userInfo ui " +
            "LEFT JOIN u.wishes w " +
            "LEFT JOIN u.friends f " +
            "LEFT JOIN f.userInfo fu " +
            "LEFT JOIN f.wishes fw " +
            "WHERE u.id = :id AND" +
            "  (LOWER(f.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
            "  (LOWER(f.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
            "  (LOWER(f.userInfo.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
            "  (LOWER(f.userInfo.country) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "GROUP BY f.id, fu.image, f.lastName, f.firstName, f.isBlocked " +
            "ORDER BY f.id DESC")
    List<UserResponseGetAll> searchFriends(String keyWord, Long id);
}