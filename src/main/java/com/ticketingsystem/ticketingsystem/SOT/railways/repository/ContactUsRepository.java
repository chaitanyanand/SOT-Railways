package com.ticketingsystem.ticketingsystem.SOT.railways.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketingsystem.ticketingsystem.SOT.railways.model.ContactUs;
@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs,Long> {

	//@Query(value="select SUM(b.no_of_seats_booked) from Booking b WHERE b.departure_date=?1 AND b.train_name=?2 AND b.departure_station_name=?3 AND b.destination_station_name=?4",nativeQuery=true)

	

}

