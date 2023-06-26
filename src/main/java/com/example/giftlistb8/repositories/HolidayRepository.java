package com.example.giftlistb8.repositories;

import com.example.giftlistb8.dto.holiday.response.GlobalSearchHoliday;
import com.example.giftlistb8.dto.holiday.response.HolidayResponse;
import com.example.giftlistb8.entities.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;


public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    @Query("SELECT new com.example.giftlistb8.dto.holiday.response.HolidayResponse(h.id,h.name, h.image, h.date )" +
            "FROM Holiday h " +
            "JOIN h.user u " +
            "WHERE u.id = :id and LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY h.id DESC")
    List<HolidayResponse> globalSearch(@Param("keyword") String keyword, @Param("id") Long id);


    @Modifying
    @Query(nativeQuery = true,value = "DELETE FROM holidays h where h.id = ?1")
    void deleteHoliday(Long id);
}

