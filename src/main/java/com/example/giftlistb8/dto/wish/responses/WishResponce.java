package com.example.giftlistb8.dto.wish.responses;

import lombok.Builder;

@Builder
public record WishResponce (
    Long id,
    String name,
    String image,
    Boolean status){
}
