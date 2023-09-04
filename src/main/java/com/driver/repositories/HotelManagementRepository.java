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

        String result = "";  // Initialize the result as an empty string
        int maxFacilities = 0;  // Initialize the maximum facilities as 0

        for (Map.Entry<String, Hotel> entry : hotelHashMap.entrySet()) {
            Hotel hotel = entry.getValue();
            List<Facility> facilities = hotel.getFacilities();
            int avaliableFacility=facilities.size();

            // Check if this hotel has more facilities
            if (avaliableFacility > maxFacilities) {
                maxFacilities = avaliableFacility;
                result = hotel.getHotelName();
            }
            // Check if this hotel has the same facilities but a lexicographically smaller name
            else if (avaliableFacility == maxFacilities && hotel.getHotelName().compareTo(result) < 0) {
                result = hotel.getHotelName();
            }
        }

        return result;

    }
    public int bookARoom(Booking booking){
        String bookingId = UUID.randomUUID().toString();
        String hotelName=booking.getHotelName();
        Hotel hotel=hotelHashMap.get(hotelName);

        if(hotel.getAvailableRooms()<booking.getNoOfRooms()) {
            return -1;
        }
            int remainingRooms = hotel.getAvailableRooms()-booking.getNoOfRooms();
            hotel.setAvailableRooms(remainingRooms);
            String name = hotel.getHotelName();
            hotelHashMap.put(name,hotel);
            booking.setBookingId(bookingId);
            // Total amout to be paid
            int amountTobePaid = booking.getNoOfRooms()*hotel.getPricePerNight();
            booking.setAmountToBePaid(amountTobePaid);
            bookingHashMap.put(bookingId,booking);
            return amountTobePaid;
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
