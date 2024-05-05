package com.ticketingsystem.ticketingsystem.SOT.railways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ticketingsystem.ticketingsystem.SOT.railways.model.Ticket;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	//@Query(value="select SUM(b.no_of_seats_booked) from Ticket b WHERE b.departure_date=?1 AND b.train_name=?2 AND b.departure_station_name=?3 AND b.destination_station_name=?4",nativeQuery=true)

	@Query("select SUM(noOfSeatsBooked) from Ticket  WHERE departureDate=?1 and trainName=?2 and departureStationName=?3 and  destinationStationName=?4")
	public String getNumberOfSeatsBooked(String departureDate, String trainName, String departureStationName,
			String destinationStationName);

	@Query("select b from Ticket b  WHERE b.userId=?1 ")
	public List<Ticket> findTicketsByUserId(Long userId);
	
	@Query("select SUM(b.fare) from Ticket b WHERE b.departureStationName=?1 AND (b.departureDate BETWEEN ?2 AND ?3)")
	public String getRevenue(String departureStationName,String startDate,String endDate);
	
	@Query("select SUM(b.noOfSeatsBooked) from Ticket b WHERE b.departureStationName=?1 AND (b.departureDate BETWEEN ?2 AND ?3)")
	public String getFootfall(String departureStationName,String startDate,String endDate);
	
	@Query("select SUM(b.noOfSeatsBooked) from Ticket b WHERE b.departureStationName=?1 AND b.departureDate=?2 AND b.departureTime >=?3 AND b.departureTime <?4" )
	public String getHourlyFootfall(String departureStationName, String Date,String start,String close);

}
