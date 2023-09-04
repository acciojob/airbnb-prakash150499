package com.driver.repositories;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {

    HashMap<String,Hotel>hotelDb=new HashMap<>();
    HashMap<Integer,User> userDb=new HashMap<>();
    HashMap<String,Booking> bookingDb=new HashMap<>();
    HashMap<Integer,List<Booking>>userBookingDb=new HashMap<>();
    public String addHotel(Hotel hotel) {
        //You need to add an hotel to the database
        //incase the hotelName is null or the hotel Object is null return an empty a FAILURE

        if(hotel==null)
        {
            return "FAILURE";
        }
        if(hotel.getHotelName()==null)
        {
            return "FAILURE";
        }
        //Incase somebody is trying to add the duplicate hotelName return FAILURE
        String hotelName=hotel.getHotelName();
        if(hotelDb.containsKey(hotelName))
        {
            return "FAILURE";
        }
        //in all other cases return SUCCESS after successfully adding the hotel to the hotelDb.
        hotelDb.put(hotelName,hotel);

        return "SUCCESS";
    }


    public Integer addUser(User user) {
        //You need to add a User Object to the database
        int aadharNo=user.getaadharCardNo();
        userDb.put(aadharNo,user);
        //Assume that user will always be a valid user and return the aadharCardNo of the user
        return aadharNo;
    }

    public String getHotelWithMostFacilities() {
        //Out of all the hotels we have added so far, we need to find the hotelName with most no of facilities
        //Incase there is a tie return the lexicographically smaller hotelName
        //Incase there is not even a single hotel with atleast 1 facility return "" (empty string)
        String result="";
        int maxFacilities=0;
        for(Map.Entry<String,Hotel> entry :hotelDb.entrySet())
        {
            Hotel currentHotel=entry.getValue();
            List<Facility>facilities=currentHotel.getFacilities();
            if(facilities.size()>maxFacilities)
            {
                maxFacilities=facilities.size();
                result= entry.getKey();
            }
            else if (facilities.size() == maxFacilities) {
                // Found a hotel with the same number of facilities, compare names lexicographically
                if (currentHotel.getHotelName().compareTo(result) < 0) {
                    result = currentHotel.getHotelName();
                }
            }

        }
        return result;
    }

    public int bookARoom(Booking booking) {
        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);
        //save the booking Entity and keep the bookingId as a primary key
        bookingDb.put(bookingId,booking);
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        String hotelName=booking.getHotelName();
        Hotel hotel=hotelDb.get(hotelName);

        int pricePerNight=hotel.getPricePerNight();
        int totalPrice=booking.getNoOfRooms()*pricePerNight;
        booking.setAmountToBePaid(totalPrice);
        bookingDb.put(bookingId,booking);

        if(hotel.getAvailableRooms()<booking.getNoOfRooms())
        {
            return -1;
        }
        hotel.setAvailableRooms(hotel.getAvailableRooms()-booking.getNoOfRooms());
        int userName=booking.getBookingAadharCard();
        associateBookingWithUser(userName,booking);
        //If there arent enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid

        return totalPrice;
    }

    private void associateBookingWithUser(int aadharNo, Booking booking) {

        if(userBookingDb.containsKey(aadharNo))
        {
            userBookingDb.get(aadharNo).add(booking);
        }
        else
        {
            List<Booking>bookingList=new ArrayList<>();
            bookingList.add(booking);
            userBookingDb.put(aadharNo,bookingList);
        }
    }

    public int getBookings(Integer aadharCard) {

        int bookingsPerUser=userBookingDb.get(aadharCard).size();
        return 0;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {

        Hotel currentHotel=hotelDb.get(hotelName);
        currentHotel.setFacilities(newFacilities);
        return currentHotel;
    }
}
