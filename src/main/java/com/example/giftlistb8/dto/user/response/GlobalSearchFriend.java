package com.example.giftlistb8.dto.user.response;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record GlobalSearchFriend(
        Long id,
        String fullName,
        String image,
        String phoneNumber,
        LocalDate dateOfBirth,
        String country,
        String wishName,
        String holidayName,
        String charityName,
        String hobby
) {
    public GlobalSearchFriend(Long id, String fullName, String image, String phoneNumber, LocalDate dateOfBirth, String country, String wishName, String holidayName, String charityName, String hobby) {
        this.id = id;
        this.fullName = fullName;
        this.image = image;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.wishName = wishName;
        this.holidayName = holidayName;
        this.charityName = charityName;
        this.hobby = hobby;
    }
}
