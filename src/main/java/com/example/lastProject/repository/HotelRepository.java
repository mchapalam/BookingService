package com.example.lastProject.repository;

import com.example.lastProject.entity.Hotel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {

    @Query("SELECT h FROM Hotel h " +
            "WHERE (:id IS NULL OR h.id = :id) " +
            "AND (:name IS NULL OR h.name LIKE %:name%) " +
            "AND (:title IS NULL OR h.title LIKE %:title%) " +
            "AND (:city IS NULL OR h.city LIKE %:city%) " +
            "AND (:address IS NULL OR h.address LIKE %:address%) " +
            "AND (:distanceFromCenter IS NULL OR h.distanceFromCenter = :distanceFromCenter) " +
            "AND (:rating IS NULL OR h.rating = :rating) " +
            "AND (:numberRatings IS NULL OR h.numberRatings = :numberRatings)")
    List<Hotel> filterHotels(@Param("id") UUID id,
                             @Param("name") String name,
                             @Param("title") String title,
                             @Param("city") String city,
                             @Param("address") String address,
                             @Param("distanceFromCenter") Double distanceFromCenter,
                             @Param("rating") Long rating,
                             @Param("numberRatings") Long numberRatings);

    @Query("SELECT h FROM Hotel h " +
            "ORDER BY h.distanceFromCenter ASC")
    List<Hotel> filterHotelsByDistanceFromCenterASC();

    @Query("SELECT h FROM Hotel h " +
            "ORDER BY h.rating ASC")
    List<Hotel> filterHotelsByRatingASC();

    @Query("SELECT h FROM Hotel h " +
            "ORDER BY h.numberRatings ASC")
    List<Hotel> filterHotelsByNumberRatingsASC();

    @Query("SELECT h FROM Hotel h " +
            "ORDER BY h.distanceFromCenter DESC")
    List<Hotel> filterHotelsByDistanceFromCenterDESC();

    @Query("SELECT h FROM Hotel h " +
            "ORDER BY h.rating DESC")
    List<Hotel> filterHotelsByRatingDESC();

    @Query("SELECT h FROM Hotel h " +
            "ORDER BY h.numberRatings DESC")
    List<Hotel> filterHotelsByNumberRatingsDESC();
}
