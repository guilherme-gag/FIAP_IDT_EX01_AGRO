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
    //Método que sera executado sempre que existir um item na fila de leituras
    @RabbitListener(queues = RabbitmqConstantes.FILA_LEITURAS)
    private void consumidor(LeituraDto leituraDto){
        //verifica se deve ser gerado um alerta
        boolean alertaTemperatura = leituraDto.temperatura >= 35 || leituraDto.temperatura <= 0 ? true : false;
        boolean alertaUmidade = leituraDto.umidade <= 15 ? true : false;
        //caso necessário alerta, envia email
        if(alertaTemperatura || alertaUmidade) {
            //monta mensagem
            String alerta = "O drone de ID " + String.valueOf( leituraDto.droneId) + " apresentou "
                    + "temperatura:" + String.valueOf( leituraDto.temperatura) + "ºC"
                    + ", umidade: " + String.valueOf( leituraDto.umidade) + "%"
                    + ", latitude: " + String.valueOf( leituraDto.latitude)
                    + ", longitude: " + String.valueOf( leituraDto.longitude)
                    + ", Rastreamento Ativado: " +  (leituraDto.ativarRastreamento  ? "Sim" : "Não");
            //Imprime alerta
            System.out.println(alerta);
            //Envia alerta por email
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setText(alerta);
            mensagem.setSubject("Alerta Drone Id" + String.valueOf( leituraDto.droneId));
            mensagem.setTo("EmailQueVaiReceber@email.com");
            mensagem.setFrom("EmailQueVaiEnviar@email.com");
            try {
                mailSender.send(mensagem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
