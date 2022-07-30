package com.agro.agroconsumer.consumer;

import constantes.RabbitmqConstantes;
import dto.LeituraDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class LeituraConsumer {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${EMAIL_USER}")
    private String emailUser;

    @RabbitListener(queues = RabbitmqConstantes.FILA_LEITURAS)

    private void consumidor(LeituraDTO leituraDTO) {

        boolean sendEmail = checkSendEmail(leituraDTO);

        if (sendEmail) {

            String alerta = emailBuilder(leituraDTO);

            System.out.println(alerta);

            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setText(alerta);
            mensagem.setSubject("Alerta Drone Id" + leituraDTO.getDroneId());
            mensagem.setTo(emailUser);
            mensagem.setFrom(emailUser);

            try {
                mailSender.send(mensagem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkSendEmail(LeituraDTO leituraDTO) {

        boolean alertaTemperatura = leituraDTO.getTemperatura() >= 35 || leituraDTO.getTemperatura() <= 0 ? true : false;
        boolean alertaUmidade = leituraDTO.getUmidade() <= 15 ? true : false;

        return alertaTemperatura || alertaUmidade;
    }

    private static String emailBuilder(LeituraDTO leituraDTO){

        StringBuilder sb = new StringBuilder();

        sb.append("O drone de ID ").append(leituraDTO.getDroneId()).append(" apresentou ")
        .append("temperatura ").append(leituraDTO.getTemperatura()).append("ºC")
        .append(", umidade: ").append(leituraDTO.getUmidade()).append("%")
        .append(", latitude: ").append(leituraDTO.getLatitude())
        .append(". Rastreamento Ativado: ").append(leituraDTO.isAtivarRastreamento()).toString();

        return sb.toString();
    }

}
