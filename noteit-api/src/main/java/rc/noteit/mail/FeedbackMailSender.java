package rc.noteit.mail;

import java.util.Properties;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMailSender implements FeedbackSender {
    private JavaMailSenderImpl mailSender;

    public FeedbackMailSender(Environment environment){
        mailSender = new JavaMailSenderImpl();

        mailSender.setHost(environment.getProperty("spring.mail.host"));
        mailSender.setPort(Integer.parseInt(environment.getProperty("spring.mail.port")));
       
        mailSender.setUsername(environment.getProperty("spring.mail.username"));
        mailSender.setPassword(environment.getProperty("spring.mail.password"));
        
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
       // props.put("mail.debug", "true");
    }

    @Override
    public void sendFeedback(String from, String name, String feedback){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("sohrabhusain123@gmail.com");
        message.setSubject("New feedback from " + from);
        message.setText("Name : "+name+"Feedback: "+feedback);
       //message.setCc(from);
        message.setFrom(name);
       
        this.mailSender.send(message);
    }
}
