package com.driver.repositories;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {
    HashMap<String,Hotel> hotelHashMap = new HashMap<>();
    HashMap<Integer,User> userHashMap = new HashMap<>();
    HashMap<String,Booking> bookingHashMap = new HashMap<>();
    public String addHotel(Hotel hotel) {

        if (hotel.getHotelName() == null) return "FAILURE";
        if (hotelHashMap.containsKey(hotel.getHotelName())) return "FAILURE";
        String hotelName = hotel.getHotelName();
        hotelHashMap.put(hotelName, hotel);
        return "SUCCESS";
    }
    public Integer addUser(User user){
        userHashMap.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }
    public String getHotelWithMostFacilities(){
        int max = 0;
        for(Hotel h : hotelHashMap.values()){
            List<Facility> list = h.getFacilities();
            max = Math.max(max,list.size());
        }
        List<String>al = new ArrayList<>();
        if(max == 0) return "";
        for(Hotel h : hotelHashMap.values()){
            List<Facility>list = h.getFacilities();
            if(list.size() == max)
                al.add(h.getHotelName());
        }
        Collections.sort(al);
        return al.get(0);

    }
    public int bookARoom(Booking booking){
        if(!hotelHashMap.containsKey(booking.getHotelName())){
            return -1;
        }
        Hotel h = hotelHashMap.get(booking.getHotelName());
        if(h.getAvailableRooms()>=booking.getNoOfRooms()){
            int remainingrooms = h.getAvailableRooms()-booking.getNoOfRooms();
            h.setAvailableRooms(remainingrooms);
            String name = h.getHotelName();
            hotelHashMap.put(name,h);
            String ss = UUID.randomUUID()+"";
            int amount = booking.getNoOfRooms()*h.getPricePerNight();
            booking.setBookingId(ss);
            booking.setAmountToBePaid(amount);
            bookingHashMap.put(ss,booking);
            return amount;
        }
        return -1;

    }
    public int getBookings(int a){
        int count = 0;
        for(Booking b : bookingHashMap.values()){
            if(b.getBookingAadharCard() == a){
                count++;
            }
        }
        return count;
    }
    public Hotel updateFacilities(List<Facility>newFacilities,String hotelName){

        if(!hotelHashMap.containsKey(hotelName))return null;

        Hotel hotel = hotelHashMap.get(hotelName);

        List<Facility> list = hotel.getFacilities();

        for(int i=0;i<newFacilities.size();i++){

            if(!list.contains(newFacilities.get(i))){

                list.add(newFacilities.get(i));
            }

        }

        hotel.setFacilities(list);

        hotelHashMap.put(hotelName,hotel);

        return  hotel;


    }
}
