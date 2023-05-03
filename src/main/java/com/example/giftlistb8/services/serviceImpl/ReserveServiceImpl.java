package com.example.giftlistb8.services.serviceImpl;

import com.example.giftlistb8.config.JwtService;
import com.example.giftlistb8.dto.PaginationResponse;
import com.example.giftlistb8.dto.SimpleResponse;
import com.example.giftlistb8.dto.reserve.requests.ReserveRequestCharity;
import com.example.giftlistb8.dto.reserve.requests.ReserveRequestWish;
import com.example.giftlistb8.dto.reserve.response.*;
import com.example.giftlistb8.entities.*;
import com.example.giftlistb8.exceptions.NotFoundException;
import com.example.giftlistb8.repositories.CharityRepository;
import com.example.giftlistb8.repositories.ReserveRepository;
import com.example.giftlistb8.repositories.WishRepository;
import com.example.giftlistb8.services.ReserveService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@Transactional
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService {
    private final ReserveRepository reserveRepository;
    private final WishRepository wishRepository;
    private final JwtService jwtService;
    private final CharityRepository charityRepository;

    @Override
    public SimpleResponse wishReserve(ReserveRequestWish reserveRequest) {
        User userInToken = jwtService.getUserInToken();
        Wish wish = wishRepository.findById(reserveRequest.wishId()).orElseThrow(
                () -> new NotFoundException(String.format("Wish with %s id not found", reserveRequest.wishId())));
        Reserve reserve = Reserve.builder()
                .isAnonymous(reserveRequest.isAnonymous())
                .wish(wish)
                .user(userInToken)
                .build();
        reserveRepository.save(reserve);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message(String.format("Gift with id %s successfully reserved", reserveRequest.wishId()))
                .build();
    }

    @Override
    public SimpleResponse charityReserve(ReserveRequestCharity reserveRequestCharity) {
        User user = jwtService.getUserInToken();
        Charity charity = charityRepository.findById(reserveRequestCharity.charityId()).orElseThrow(
                () -> new NotFoundException(String.format("Charity with %s id not found", reserveRequestCharity.charityId())));
        Reserve reserve = new Reserve();
        reserve.setUser(user);
        reserve.setCharity(charity);
        reserve.setIsAnonymous(true);
        reserveRepository.save(reserve);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message(String.format("Charity with  %s  id successfully reserved", reserveRequestCharity.charityId()))
                .build();
    }

    @Override
    public ReserveGetAllResponse getAllReserves() {
        return new ReserveGetAllResponse(reserveRepository.getAllReversesWish(), reserveRepository.getAllReversesCharity());
    }

    @Transactional
    @Override
    public SimpleResponse addGiftToWish(Long wishId) {
        User userInToken = jwtService.getUserInToken();
        Wish wish = wishRepository.findById(wishId).orElseThrow(
                () -> new NotFoundException(String.format("Wish with %s id not found", wishId)));
        Wish newWish = new Wish();
        newWish.setName(wish.getName());
        newWish.setImage(wish.getImage());
        newWish.setDescription(wish.getDescription());
        newWish.setLinkGift(wish.getLinkGift());
        newWish.setUser(userInToken);
        wishRepository.save(newWish);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message(String.format("Gift with %s id successfully added to wish from reserves", wishId))
                .build();
    }


    @Override
    public PaginationResponse getWishReservePagination(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ReserveResponseWish> pagedWishes = reserveRepository.getAll(pageable);
        return PaginationResponse.builder()
                .elements(Collections.singletonList(pagedWishes.getContent()))
                .pageSize(pagedWishes.getNumber() + 1)
                .currentPage(pagedWishes.getTotalPages())
                .build();
    }


    @Override
    public PaginationResponse getCharityReservePagination(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ReserveResponseCharity> pagedCharity = reserveRepository.getAllCharity(pageable);
        return PaginationResponse.builder()
                .elements(Collections.singletonList(pagedCharity.getContent()))
                .currentPage(pagedCharity.getTotalPages())
                .pageSize(pagedCharity.getNumber() + 1)
                .build();
    }
}



