package com.agro.agroproducer.service;

import constantes.RabbitmqConstantes;
import dto.LeituraDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeituraService {

    @Autowired
    private RabbitmqService rabbitmqService;

    public boolean enviarMensagem(LeituraDto leituraDto)
    {
        boolean valido = validar(leituraDto);
        if(valido) {
            this.rabbitmqService.enviaMensagem(RabbitmqConstantes.FILA_LEITURAS, leituraDto);
        }
        return valido;
    }

    public  boolean validar(LeituraDto leitura){
        boolean valido = true;
        return  valido;
    }
}
