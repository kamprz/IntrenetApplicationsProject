package wat.semestr8.tim.services.ticketsending.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailClient {

    private static String FROM = "filharmonia.narodowa@o2.pl";
    private Message message;
    private static final String ticketPath = "./ticket.pdf";

    private Properties prop () {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.host", "poczta.o2.pl");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.trust", "poczta.o2.pl");
        return prop;
    }

    private Session makeSession() {
        return Session.getInstance(prop(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("filharmonia.narodowa@o2.pl",
                        "MojeWlasneHaslo123");
            }
        });
    }

    //path example : ./resources/ticket.pdf
    private Message prepareMessage(String receiver) throws MessagingException, IOException {
        Message message = new MimeMessage(makeSession());
        message.setFrom(new InternetAddress(FROM));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject("Bilety do Filharmonii");

        String msg = "W zalaczniku znajduje sie twoj bilet";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html");

        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        attachmentBodyPart.attachFile(new File(ticketPath));

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        multipart.addBodyPart(attachmentBodyPart);

        message.setContent(multipart);
        return message;
    }

    public EmailClient(String to) throws IOException, MessagingException {
            message = prepareMessage(to);
    }

    public void send () throws MessagingException {
        Transport.send(message);
    }

}
