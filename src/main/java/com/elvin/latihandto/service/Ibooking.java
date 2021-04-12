package com.elvin.latihandto.service;

import com.elvin.latihandto.model.Booking;
import com.elvin.latihandto.model.Category;
import com.elvin.latihandto.model.nativeQuery.GetProductName;

import java.util.List;
import java.util.Optional;

public interface Ibooking {
    List<Booking> findAll();
    Booking save(Booking booking);
    List<GetProductName>  getProductName(int id);
    Optional<Booking> findById(int id);
}
