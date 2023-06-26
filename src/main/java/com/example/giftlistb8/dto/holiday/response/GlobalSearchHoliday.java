package com.example.giftlistb8.dto.holiday.response;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record GlobalSearchHoliday(
        Long id,
        String holidayName,
        String image,
        LocalDate date
) {
    public GlobalSearchHoliday(Long id, String holidayName, String image, LocalDate date) {
        this.id = id;
        this.holidayName = holidayName;
        this.image = image;
        this.date = date;
    }
}
