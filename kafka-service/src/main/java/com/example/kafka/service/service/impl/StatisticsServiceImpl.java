package com.example.kafka.service.service.impl;

import com.example.kafka.service.entity.Booking;
import com.example.kafka.service.entity.UserInfo;
import com.example.kafka.service.repository.BookingRepository;
import com.example.kafka.service.repository.UserRepository;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl {

    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    public List<UserInfo> getAllUserRegistrations() {
        return userRepository.findAll();
    }

    public List<Booking> getAllRoomBookings() {
        return bookingRepository.findAll();
    }

    public String exportStatisticsToCSV() throws IOException {
        List<Booking> roomBookings = getAllRoomBookings();
        String csvFile = "room_bookings.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            // Write header
            writer.writeNext(new String[]{"ID", "User ID", "Check In Date", "Check Out Date", "Room ID"});
            // Write records
            for (Booking booking : roomBookings) {
                writer.writeNext(new String[]{
                        booking.getUserId().toString(),
                        booking.getCheckInDate().toString(),
                        booking.getCheckOutDate().toString(),
                        booking.getRoomId().toString()
                });
            }
        }
        return csvFile;
    }

    public void saveUser(UserInfo userInfo) {
        userRepository.save(userInfo);
    }

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }
}
