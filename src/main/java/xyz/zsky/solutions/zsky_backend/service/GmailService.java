package xyz.zsky.solutions.zsky_backend.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

@Service
public class GmailService {

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.refresh.token}")
    private String refreshToken;

    @Value("${google.application.name}")
    private String applicationName;

    @Value("${google.sender.email}")
    private String senderEmail;

    @Value("${google.recipient.email}")
    private String recipientEmail;

    private Gmail gmailServiceInstance;

    private final JsonFactory JSON_FACTORY = new GsonFactory();

    private Gmail getGmailServiceInstance() throws Exception {
        if (gmailServiceInstance == null) {
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setClientSecrets(clientId, clientSecret)
                    .setJsonFactory(JSON_FACTORY)
                    .setTransport(new NetHttpTransport())
                    .build()
                    .setRefreshToken(refreshToken);

            if (credential.getAccessToken() == null || credential.getExpiresInSeconds() <= 60) {
                credential.refreshToken();
            }

            gmailServiceInstance = new Gmail.Builder(new NetHttpTransport(), JSON_FACTORY, credential)
                    .setApplicationName(applicationName)
                    .build();
        }
        return gmailServiceInstance;
    }

    public void sendConfirmationEmail(String name, String email, String message) throws Exception {
        String subject = "New Contact Form Submission from ZSky Solutions Website";
        String emailBody = String.format(
                "Hello ZSky Solutions Team,\n\n" +
                "You have received a new contact form submission with the following details:\n\n" +
                "Name: %s\n" +
                "Email: %s\n" +
                "Message:\n%s\n\n" +
                "Please reach out to them as soon as possible.",
                name, email, message
        );

        MimeMessage emailContent = createMimeEmail(recipientEmail, senderEmail, subject, emailBody);
        sendMessageViaGmailApi(emailContent);
        System.out.println("Confirmation email sent successfully to " + recipientEmail);
    }

    private MimeMessage createMimeEmail(String to, String from, String subject, String bodyText) throws Exception {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    private void sendMessageViaGmailApi(MimeMessage emailContent) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);

        Message message = new Message();
        message.setRaw(encodedEmail);

        getGmailServiceInstance().users().messages().send(senderEmail, message).execute();
    }
}
