package com.elvin.latihandto.response;

import lombok.Data;

@Data
public class BookingResponse {
    private String name;
    private String from;
    private int productId;
    private String productName;
    private int qty;
    private String status;
    private String booking_msg;
    private String response_msg;

}
