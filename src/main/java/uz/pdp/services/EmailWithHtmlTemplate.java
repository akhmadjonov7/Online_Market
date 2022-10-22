package uz.pdp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import uz.pdp.dtos.EmailDto;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
@Service
@RequiredArgsConstructor
@Primary
public class EmailWithHtmlTemplate implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendEmail(EmailDto emailDto) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Map<String, Object> model = new HashMap<>();
            model.put("name", emailDto.getName());
            model.put("message", emailDto.getMessage());
            model.put("id",emailDto.getId());

            Context context = new Context();
            context.setVariables(model);

            String html = templateEngine.process("verify", context);

            helper.setFrom("oybekakhmadjonov1@gmail.com");
            helper.setTo(emailDto.getTo());
            helper.setSubject(emailDto.getSubject());
            helper.setText(html, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new IllegalStateException("Something went wrong!!!");
        }
    }
}
