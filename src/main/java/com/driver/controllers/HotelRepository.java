package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelRepository {
    HashMap<String, Hotel> hotelMap=new HashMap<>();
    HashMap<Integer, User> userMap=new HashMap<>();
    HashMap<Integer, List<String>> personToBookingMap=new HashMap<>();
    HashMap<String, Integer> bookingToPersonMap=new HashMap<>();


    public String addHotel(Hotel hotel) {
        //You need to add an hotel to the database
        //incase the hotelName is null or the hotel Object is null return an empty a FAILURE
        //Incase somebody is trying to add the duplicate hotelName return FAILURE
        //in all other cases return SUCCESS after successfully adding the hotel to the hotelDb.

        if( hotel.getHotelName()==null  ||hotel==null  ||hotel.getHotelName().equals("") ){
            return "FAILURE";
        }else if(hotelMap.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }else {

            hotelMap.put(hotel.getHotelName(),hotel);
            return "SUCCESS";
        }
    }

    public void addUser(User user) {
        if(!userMap.containsKey(user.getaadharCardNo())){
            personToBookingMap.put(user.getaadharCardNo(),new ArrayList<>());
            userMap.put(user.getaadharCardNo(),user);
        }
    }

    public int bookARoom(Booking booking) {
        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        //If there arent enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid

        String hotelname=booking.getHotelName();
        if(hotelMap.get(hotelname).getAvailableRooms()<booking.getNoOfRooms()){
            return -1;
        }
        int amountToBePaid=booking.getNoOfRooms()*hotelMap.get(hotelname).getPricePerNight();
        personToBookingMap.get(booking.getBookingAadharCard()).add(booking.getBookingId());
        bookingToPersonMap.put(booking.getBookingId(),booking.getBookingAadharCard());
        return amountToBePaid;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        //We are having a new facilites that a hotel is planning to bring.
        //If the hotel is already having that facility ignore that facility otherwise add that facility in the hotelDb
        //return the final updated List of facilities and also update that in your hotelDb
        //Note that newFacilities can also have duplicate facilities possible
        Hotel curHotel=hotelMap.get(hotelName);
        HashSet<Facility> set=new HashSet<>();
        for(Facility fac:curHotel.getFacilities()){
            set.add(fac);
        }

        for (Facility fac:newFacilities){
            if(!set.contains(fac)){
                curHotel.getFacilities().add(fac);
                set.add(fac);
            }
        }
        return curHotel;

    }

    public int getBookings(Integer aadharCard) {
        if(!personToBookingMap.containsKey(aadharCard)) return 0;
        else {
            return personToBookingMap.get(aadharCard).size();
        }
    }

    class Pair{
        int noOfFac;
        String hotelName;
        Pair(int noOfFac,String hotelName){
            this.hotelName=hotelName;
            this.noOfFac=noOfFac;
        }
    }
    public String getHotelWithMostFacilities() {
        //Out of all the hotels we have added so far, we need to find the hotelName with most no of facilities
        //Incase there is a tie return the lexicographically smaller hotelName
        //Incase there is not even a single hotel with atleast 1 facility return "" (empty string)
        Stack<Pair> stack=new Stack<>();
        stack.add(new Pair(0,""));
            for (String hotelname:hotelMap.keySet()){
                int curNoOfFac = hotelMap.get(hotelname).getFacilities().size();
                if(stack.peek().noOfFac<curNoOfFac){
                    stack.clear();
                    stack.add(new Pair(curNoOfFac,hotelname));
                }else if(stack.peek().noOfFac==curNoOfFac){
                    stack.add(new Pair(curNoOfFac,hotelname));
                }
            }

            if(stack.peek().noOfFac==0) return "";
            List<String> resList=new ArrayList<>();
            while(stack.size()>0){
                resList.add(stack.pop().hotelName);
            }
            Collections.sort(resList);
            return resList.get(0);
    }
}

























