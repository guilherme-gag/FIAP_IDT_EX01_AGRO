package com.agro.agroproducer.service;

import constantes.RabbitmqConstantes;
import dto.LeituraDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Service
public class LeituraService {

    @Autowired
    private RabbitmqService rabbitmqService;

    public boolean sendMessage(LeituraDTO leituraDTO) {

        boolean isValid = validate(leituraDTO);
        leituraDTO.setValido(isValid);

        System.out.println(leituraDTO.toString());

        boolean emailSeraEnviado = checkSendEmail(leituraDTO);

        if(emailSeraEnviado){
            System.out.println("SERA ENVIADO" + leituraDTO.toString());
        }


        if (isValid) {
            this.rabbitmqService.sendMessage(RabbitmqConstantes.FILA_LEITURAS, leituraDTO);
        }
        return isValid;
    }

    public boolean validate(@Valid LeituraDTO leituraDTO) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        return validator.validate(leituraDTO).isEmpty();

    }
    private boolean checkSendEmail(LeituraDTO leituraDTO) {

        boolean alertaTemperatura = leituraDTO.getTemperatura() >= 35 || leituraDTO.getTemperatura() <= 0 ? true : false;
        boolean alertaUmidade = leituraDTO.getUmidade() <= 15 ? true : false;
        boolean valido = leituraDTO.isValido();

        return (alertaTemperatura || alertaUmidade) && valido;
    }

}
