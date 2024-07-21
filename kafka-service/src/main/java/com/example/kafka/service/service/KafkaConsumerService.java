package com.example.kafka.service.service;

import com.example.kafka.service.entity.Booking;
import com.example.kafka.service.entity.UserInfo;
import com.example.kafka.service.service.impl.StatisticsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final StatisticsServiceImpl statisticsService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user-registration", groupId = "group_id")
    public void listenUserRegistration(String message) {
        try {
            UserInfo userInfo = objectMapper.readValue(message, UserInfo.class);
            System.out.println("Received User Registration: " + message);

            statisticsService.saveUser(userInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @KafkaListener(topics = "room-booking", groupId = "group_id")
    public void listenRoomBooking(String message) {
        try {
            Booking booking = objectMapper.readValue(message, Booking.class);
            System.out.println("Received Room Booking: " + booking);

            statisticsService.saveBooking(booking);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}