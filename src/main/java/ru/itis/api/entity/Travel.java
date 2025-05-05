package ru.itis.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "total_budget", nullable = false)
    private Long totalBudget;

    @Column(name = "date_of_begin", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBegin;

    @Column(name = "date_of_end", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfEnd;

    @OneToMany
    @JoinColumn(name="user_travel_id")
    private List<UserTravel> users;

}
