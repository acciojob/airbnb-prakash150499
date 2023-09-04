package com.driver.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Servicelayer {
   // @Autowired
    Repo repo = new Repo();
    public String addHotel(Hotel hotel){
        return repo.addHotel(hotel);

    }
    public Integer addUser(User user){
         return repo.addUser(user);
    }
    public String getHotelWithMostFacilities(){
        return repo.getHotelWithMostFacilities();
    }
    public int bookARoom(Booking booking){
        return repo.bookARoom(booking);

    }
    public int getBookings(int a){
        return repo.getBookings(a);
    }
    public Hotel updateFacilities(List<Facility>face, String hotel){
    return repo.updateFacilities(face,hotel);
    }

}
