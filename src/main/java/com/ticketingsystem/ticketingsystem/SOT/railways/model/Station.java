package com.ticketingsystem.ticketingsystem.SOT.railways.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;


@Entity
@Table(name="station")
public class Station implements Comparable<Station> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String name;
	private String firstTrainDepartureTime;
	private String firstReverseTrainDepartureTime;
	
	
	public Station() {
		super();
	}


	public Station(Long id, String name, String firstTrainDepartureTime, String firstReverseTrainDepartureTime) {
		super();
		Id = id;
		this.name = name;
		this.firstTrainDepartureTime = firstTrainDepartureTime;
		this.firstReverseTrainDepartureTime = firstReverseTrainDepartureTime;
	}


	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFirstTrainDepartureTime() {
		return firstTrainDepartureTime;
	}


	public void setFirstTrainDepartureTime(String firstTrainDepartureTime) {
		this.firstTrainDepartureTime = firstTrainDepartureTime;
	}


	public String getFirstReverseTrainDepartureTime() {
		return firstReverseTrainDepartureTime;
	}


	public void setFirstReverseTrainDepartureTime(String firstReverseTrainDepartureTime) {
		this.firstReverseTrainDepartureTime = firstReverseTrainDepartureTime;
	}
	
	@Override
	 public int compareTo(Station station) {
         return this.firstTrainDepartureTime.compareTo(station.getFirstTrainDepartureTime());
     }
	
    
}


