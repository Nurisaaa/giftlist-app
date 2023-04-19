package com.example.giftlistb8.repositories;

import com.example.giftlistb8.dto.wish.responses.WishResponce;
import com.example.giftlistb8.entities.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish,Long> {
    @Query("select new com.example.giftlistb8.dto.wish.responses.WishResponce(w.id,w.name,w.image,w.status) from Wish w where w.id=:id")
    Optional<WishResponce> findWishById(Long id);

    @Query("select new com.example.giftlistb8.dto.wish.responses.WishResponce(w.id,w.name,w.image,w.status) from Wish w")
    List<WishResponce> findAllWishes();
}
