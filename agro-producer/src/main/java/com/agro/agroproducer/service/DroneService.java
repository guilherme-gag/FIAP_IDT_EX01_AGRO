package com.agro.agroproducer.service;

import dto.LeituraDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroneService {

    @Autowired
    private LeituraService service;

    private LeituraDTO leitura;

    public void criarDroneSimulacao() {

        for (int i = 1; i <= 10; i++) {

            leitura = new LeituraDTO();
            leitura.setDroneId(i);
            leitura.setTemperatura(getRandomNumber(-25, 40));
            leitura.setUmidade(getRandomNumber(-10, 120));
            leitura.setLatitude(getRandomNumber(-100, 100));
            leitura.setLongitude(getRandomNumber(-190, 190));
            leitura.setAtivarRastreamento((getRandomNumber(0, 1) == 1));

            service.sendMessage(leitura);
        }


    }

    private int getRandomNumber(int min, int max) {
        int numeroAleatorio = (int) (Math.random() * max);
        if (numeroAleatorio <= min) {
            numeroAleatorio = numeroAleatorio + min;
        }
        return numeroAleatorio;
    }
}
