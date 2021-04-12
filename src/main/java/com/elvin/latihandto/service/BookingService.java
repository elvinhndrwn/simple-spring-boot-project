package com.elvin.latihandto.service;

import com.elvin.latihandto.model.Booking;
import com.elvin.latihandto.model.nativeQuery.GetProductName;
import com.elvin.latihandto.repository.BookingRepository;
import com.elvin.latihandto.service.dao.BookingQueryDAO;
import org.apache.commons.collections4.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements Ibooking{
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingQueryDAO bookingQueryDAO;

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public List<GetProductName>  getProductName(int id) {
        return bookingQueryDAO.getProductName(id);
    }

    @Override
    public Optional<Booking> findById(int id) {
        return bookingRepository.findById(id);
    }
}
