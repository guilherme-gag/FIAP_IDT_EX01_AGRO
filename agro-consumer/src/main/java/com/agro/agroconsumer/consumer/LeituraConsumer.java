package com.agro.agroconsumer.consumer;

import constantes.RabbitmqConstantes;
import dto.LeituraDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class LeituraConsumer {

    @Autowired private JavaMailSender mailSender;

    @RabbitListener(queues = RabbitmqConstantes.FILA_LEITURAS)
    private void consumidor(LeituraDto leituraDto){
        boolean alertaTemperatura = leituraDto.temperatura >= 35 || leituraDto.temperatura <= 0 ? true : false;
        boolean alertaUmidade = leituraDto.umidade <= 15 ? true : false;
        if(alertaTemperatura || alertaUmidade) {
            String alerta = "O drone de ID " + String.valueOf( leituraDto.droneId) + " apresentou "
                    + "temperatura:" + String.valueOf( leituraDto.temperatura) + "º"
                    + " e umidade: " + String.valueOf( leituraDto.umidade) + "%";
            System.out.println(alerta);
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setText(alerta);
            mensagem.setSubject("Alerta Drone Id" + String.valueOf( leituraDto.droneId));
            mensagem.setTo("emailprapessoa@email.com");
            mensagem.setFrom("emaildapessoaquetaenviando@email.com");

            try {
                mailSender.send(mensagem);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(alerta);
        }
    }
}
