package com.example.giftlistb8.dto.user.response;

import lombok.Builder;

@Builder
public record UserResponseGetAll(
        Long id,
        String photo,
        String fullName,
        int count,
        boolean isBlocked
) {
    public UserResponseGetAll(Long id, String photo, String fullName, int count, boolean isBlocked) {
        this.id = id;
        this.photo = photo;
        this.fullName = fullName;
        this.count = count;
        this.isBlocked = isBlocked;
    }
}
