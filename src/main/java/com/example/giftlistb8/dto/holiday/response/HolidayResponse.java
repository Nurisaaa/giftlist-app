package com.example.giftlistb8.dto.holiday.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record HolidayResponse(
        Long id,
        String name,
        String image,
        LocalDate date
) {
    public HolidayResponse(Long id, String name, String image, LocalDate date) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.date = date;
    }
}
