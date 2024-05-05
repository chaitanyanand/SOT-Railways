package com.ticketingsystem.ticketingsystem.SOT.railways.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketingsystem.ticketingsystem.SOT.railways.model.Train;

public interface TrainRepository extends JpaRepository<Train,Long> {

}
