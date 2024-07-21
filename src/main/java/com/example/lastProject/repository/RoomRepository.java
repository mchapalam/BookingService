package com.example.lastProject.repository;

import com.example.lastProject.entity.Room;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    @Query("SELECT r FROM Room r " +
            "WHERE (:id IS NULL OR r.id = :id)" +
            "AND (:title IS NULL OR r.title LIKE %:title%)"
    )
    List<Room> filterRooms(@Param("id") UUID id,
                           @Param("title") String title);

    @Query("SELECT r FROM Room r " +
            "ORDER BY r.id ASC")
    List<Room> findRoomsByIdASC();

    @Query("SELECT r FROM Room r " +
            "ORDER BY r.id DESC ")
    List<Room> findRoomsByIdDESC();

    @Query("SELECT r FROM Room r " +
            "ORDER BY r.hotel.id ASC")
    List<Room> findRoomsByHotelIdASC();

    @Query("SELECT r FROM Room r " +
            "ORDER BY r.hotel.id DESC ")
    List<Room> findRoomsByHotelIdDESC();

    @Query("SELECT r FROM Room r " +
            "ORDER BY r.price ASC")
    List<Room> findRoomsByPriceASC();

    @Query("SELECT r FROM Room r " +
            "ORDER BY r.price DESC ")
    List<Room> findRoomsByPriceDESC();

    @Query("SELECT r FROM Room r " +
            "ORDER BY r.countPeople ASC")
    List<Room> findRoomsByCountPeopleASC();

    @Query("SELECT r FROM Room r " +
            "ORDER BY r.countPeople DESC ")
    List<Room> findRoomsByCountPeopleDESC();
}
