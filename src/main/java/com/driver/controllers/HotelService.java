package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;
    public String addHotel(Hotel hotel) {
            return hotelRepository.addHotel(hotel);
    }

    public void addUser(User user) {
        hotelRepository.addUser(user);
    }

    public int bookARoom(Booking booking) {
        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        //If there arent enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid

        booking.setBookingId(UUID.randomUUID().toString());
        return hotelRepository.bookARoom(booking);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
            return hotelRepository.updateFacilities(newFacilities,hotelName);
    }

    public String getHotelWithMostFacilities() {
            return hotelRepository.getHotelWithMostFacilities();
    }

    public int getBookings(Integer aadharCard) {
        return hotelRepository.getBookings(aadharCard);
    }
}
