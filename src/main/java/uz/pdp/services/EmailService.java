package uz.pdp.services;


import uz.pdp.dtos.EmailDto;

public interface EmailService {

    void sendEmail(EmailDto emailDto);
}
