package com.elvin.latihandto.repository;

import com.elvin.latihandto.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
