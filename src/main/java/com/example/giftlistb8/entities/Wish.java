package com.example.giftlistb8.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "wishes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wish_id_gen")
    @SequenceGenerator(name = "wish_id_gen",
            sequenceName = "wish_id_seq",allocationSize = 1,initialValue = 23)
    private Long id;
    private String name;
    private String linkGift;
    private String image;
    private String description;
    private LocalDate dateOfHoliday;
    private Boolean status;
    private boolean isBlocked;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Complaint> complaints;

    @OneToOne(mappedBy = "wish", cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private Reserve reserve;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private Holiday holiday;
}