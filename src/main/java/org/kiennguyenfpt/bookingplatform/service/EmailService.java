package org.kiennguyenfpt.bookingplatform.service;

public interface EmailService {
    void sendPasswordResetEmail(String to, String resetLink);
}