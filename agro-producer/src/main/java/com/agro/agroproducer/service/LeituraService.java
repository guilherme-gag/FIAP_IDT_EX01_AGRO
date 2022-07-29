package com.agro.agroproducer.service;

import constantes.RabbitmqConstantes;
import dto.LeituraDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeituraService {

    @Autowired
    private RabbitmqService rabbitmqService;
    //Método que valida os dados de uma leitura e envia para o serviço Rabbitmq
    public boolean enviarMensagem(LeituraDto leituraDto)
    {
        boolean valido = validar(leituraDto);
        if(valido) {
            this.rabbitmqService.enviaMensagem(RabbitmqConstantes.FILA_LEITURAS, leituraDto);
        }
        return valido;
    }
    //Método que valida os dados de uma leitura
    public  boolean validar(LeituraDto leitura){
        //valida umidade
        if(leitura.umidade > 100 || leitura.umidade < 0)
            return false;
        //valida temperatura
        if(leitura.temperatura <-25 || leitura.temperatura > 40)
            return false;
        //valida latitude
        if(leitura.latitude > 90 || leitura.latitude < -90)
            return false;
        //valida longitude
        if(leitura.longitude > 180 || leitura.longitude <-180)
            return  false;
        return  true;
    }
}
