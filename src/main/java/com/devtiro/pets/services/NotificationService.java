package com.devtiro.pets.services;

import com.devtiro.pets.domain.entity.Address;
import com.devtiro.pets.domain.entity.AdoptionApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;

    @Async
    public void sendSubmitConfirmation(String userEmail, AdoptionApplication application) {
        try {
            Address address = application.getAddress();
            var message = new SimpleMailMessage();
            message.setTo(userEmail);
            message.setSubject("Your Application has been submitted");
            message.setText(String.format(
                    """
                            You have submitted your application with these personal information:
                            
                            Pet name: %s
                            Your Address:
                                street: %s
                                city: %s
                                state: %s
                                zipCode: %s
                            Your Email Address: %s
                            Your Phone number: %s
                            Please confirm your personal information is correct,
                              and wait for the potential staffer to approve your application request
                    """,
                    application.getPetName(),
                    address.getStreet(),
                    address.getCity(),
                    address.getState(),
                    address.getZipCode(),
                    application.getEmail(),
                    application.getPhoneNumber()
            ));

            mailSender.send(message);
            log.info("Sent confirmation notification to adopter: {}, for pet {} ", userEmail, application.getPetName());
        } catch (Exception e) {
            log.error("Failed to send viewing request notification to adopter: {}", userEmail, e);
        }
    }

}
