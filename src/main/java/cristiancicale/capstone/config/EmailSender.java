package cristiancicale.capstone.config;

import cristiancicale.capstone.entities.User;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    private final String domainName;
    private final String apiKey;

    public EmailSender(@Value("${mailgun.domain-name}") String domainName, @Value("${mailgun.api-key}") String apiKey) {
        this.domainName = domainName;
        this.apiKey = apiKey;
    }

    public void sendRegistrationEmail(User recipient) {

        try {
            HttpResponse<JsonNode> response = Unirest.post(
                            "https://api.mailgun.net/v3/" + domainName + "/messages")
                    .basicAuth("api", apiKey)
                    .field("from", "Just Music <postmaster@sandboxee337a21601640258066ee74a8c75fc2.mailgun.org>")
                    .field("to", recipient.getEmail())
                    .field("subject", "Benvenuto sulla piattaforma Just Music!")
                    .field("text",
                            "Ciao " + recipient.getName() + ", la tua registrazione è andata a buon fine!")
                    .asJson();

            if (response.getStatus() >= 200 && response.getStatus() < 300) {
                logger.info("Email inviata correttamente");
            } else {
                logger.error("Errore invio email: {}", response.getBody());
            }
        } catch (Exception e) {
            logger.error("Errore durante invio email", e);
        }
    }
}
