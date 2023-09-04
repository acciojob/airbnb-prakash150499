package com.driver.services;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repositories.HotelManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelManagementService {
    @Autowired
    HotelManagementRepository repository;
    public String addHotel(Hotel hotel) {
        return repository.addHotel(hotel);
    }

    public Integer addUser(User user) {
        return repository.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        return repository.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking) {
        return repository.bookARoom(booking);
    }

    public int getBookings(Integer aadharCard) {
        return repository.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return repository.updateFacilities(newFacilities,hotelName);
    }
}
