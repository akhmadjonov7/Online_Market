package uz.pdp.services;


import uz.pdp.dtos.EmailDto;
import uz.pdp.util.ApiResponse;

public interface EmailService {

    void sendEmail(EmailDto emailDto);
}
