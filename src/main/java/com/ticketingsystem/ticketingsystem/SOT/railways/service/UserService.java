package com.ticketingsystem.ticketingsystem.SOT.railways.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ticketingsystem.ticketingsystem.SOT.railways.model.User;
import com.ticketingsystem.ticketingsystem.SOT.railways.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}


	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public User getUserById(Long Id) {
		return userRepository.getReferenceById(Id);
	}
	
	public User getUserByEmail(String email)  {
		
		return userRepository.getUserByEmail(email);
	}
	public User registerUser(User user) {
		user.setEnabled(false);
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		return userRepository.save(user);
		
	}
	
	public void sendVerificationEmail(User user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		String subject = "Please verify your registration";
		String senderName = "SOT Railways";
        String mailContent = "<p>Dear "+user.getFirstname()+" "+user.getLastname()+",</p>";
        mailContent+="<p>Please click the link below to verify your registration:</p>";
		String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
        mailContent+="<h3><a href=\""+verifyURL+"\">VERIFY</a></h3>";
        mailContent+="<p>Thank You<br>SOT Railways</p>";
		String toAddress = user.getEmail();
		String fromAddress = "railways.sot@gmail.com";
	

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);


		helper.setText(mailContent, true);

		mailSender.send(message);

	}
	
	
	public boolean verify(String verificationCode) {
	    User user = userRepository.findByVerficationCode(verificationCode);
	    user.setVerificationCode(null);
	    if (user == null || user.isEnabled()) {
	        return false;
	    } else {
	        user.setVerificationCode(null);
	        user.setEnabled(true);
	        userRepository.save(user);
	        return true;
	    }
	     
	}
	
	public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any user with the email " + email);
        } 
    }
     
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }
     
    public void updatePassword(User user, String newPassword) {
        String encodedPassword =newPassword;
        user.setPassword(encodedPassword);
         
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
    public void deleteById(Long userId) {
    	userRepository.deleteById(userId);
    }


	public User getUserByFirstname(String firstname) {
		userRepository.getUserByFirstname(firstname);
		return null;
	}


	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
