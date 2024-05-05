package com.ticketingsystem.ticketingsystem.SOT.railways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ticketingsystem.ticketingsystem.SOT.railways.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("select u from User u where u.email=?1")
	public User getUserByEmail(String email);
	
	@Query("UPDATE  User u SET u.enabled=true WHERE u.id = ?1 ")
	@Modifying
	public void enable(Integer id);
	
	@Query("SELECT u from User u where u.verificationCode=?1")
	public User findByVerficationCode(String code);
	

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email); 
     
    public User findByResetPasswordToken(String token);

	public void getUserByFirstname(String firstname);

}
