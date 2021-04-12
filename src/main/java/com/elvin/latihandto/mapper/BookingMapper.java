package com.elvin.latihandto.mapper;

import com.elvin.latihandto.dto.BookingDTO;
import com.elvin.latihandto.model.Booking;

public class BookingMapper {

    public  static Booking DtoToEntity(BookingDTO bookingDTO){
        return  new Booking().setName(bookingDTO.getName())
                .setEmail(bookingDTO.getEmail())
                .setMsg(bookingDTO.getMsg())
                .setProductId(bookingDTO.getProductId())
                .setQty(bookingDTO.getQty())
                .setStatus("Pending");
    }

    public static BookingDTO EntityToDto(Booking booking){
        return new BookingDTO().setName(booking.getName())
                .setEmail(booking.getEmail())
                .setMsg(booking.getMsg())
                .setProductId(booking.getProductId())
                .setQty(booking.getQty())
                .setStatus("Pending");
    }
}
