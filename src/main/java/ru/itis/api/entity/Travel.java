package ru.itis.api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
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
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBegin;

    @Column(name = "date_of_end", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfEnd;

    @OneToMany(mappedBy = "travel")
    private List<UserTravel> users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="creator_id")
    private User creator;

    @Column(name = "is_ended", nullable = false)
    private Boolean isEnded;
}
