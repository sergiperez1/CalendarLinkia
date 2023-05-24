package com.example.proyectofinaldam;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class enviarCorreo {

    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
// Configuración del servidor SMTP
        final String host = "smtp.gmail.com";
        final int puerto = 587;
        final String usuario = "proyectodam.final@gmail.com";
        final String contraseña = "mnlmdpuyqgopafbn";

        // Configuración de propiedades
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", puerto);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Autenticación de correo electrónico
        Authenticator autenticador = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, contraseña);
            }
        };

        // Creación de la sesión de correo electrónico
        Session session = Session.getInstance(props, autenticador);

        try {
            // Creación del objeto MimeMessage
            MimeMessage message = new MimeMessage(session);

            // Configuración de los destinatarios, asunto y cuerpo del mensaje
            message.setFrom(new InternetAddress(usuario));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);

            // Envío del mensaje
            Transport.send(message);

            // Éxito
            System.out.println("Correo enviado con éxito.");
        } catch (MessagingException e) {
            // Error
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}