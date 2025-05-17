package ru.itis.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.api.dto.TravelDto;
import ru.itis.api.dto.TravelParticipantsDto;
import ru.itis.api.entity.Travel;

import java.util.List;
import java.util.Optional;

public interface TravelRepository extends JpaRepository<Travel, Long> {
    @Query("SELECT new ru.itis.api.dto.TravelDto(t.id, t.name, t.totalBudget, t.dateOfBegin, t.dateOfEnd) " +
            "FROM Travel t " +
            "WHERE t.id IN :travelIds AND t.isActive = true")
    List<TravelDto> findActiveTravelsByIds(@Param("travelIds") List<Long> travelIds);


    @Query("SELECT new ru.itis.api.dto.TravelDto(t.id, t.name, t.totalBudget, t.dateOfBegin, t.dateOfEnd) " +
            "FROM Travel t " +
            "WHERE t.id IN :travelIds AND t.isActive = false")
    List<TravelDto> findCompletedTravelsByIds(@Param("travelIds") List<Long> travelIds);

    @Query("""
    SELECT new ru.itis.api.dto.TravelParticipantsDto(
        t.id,
        t.name,
        t.totalBudget,
        t.dateOfBegin,
        t.dateOfEnd,
        NEW ru.itis.api.dto.UserDto(
            t.creator.phoneNumber,
            t.creator.firstName,
            t.creator.lastName
        )
    )
    FROM Travel t
    WHERE t.id = :travelId
    """)
    Optional<TravelParticipantsDto> findTravelDetailsById(@Param("travelId") Long travelId);

    boolean existsTravelByIdAndCreatorId(Long travelId,
                                         Long creatorId);
}
