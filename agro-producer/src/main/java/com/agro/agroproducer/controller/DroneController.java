package com.agro.agroproducer.controller;

import com.agro.agroproducer.service.DroneService;
import com.agro.agroproducer.service.LeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "drone")
public class DroneController {

    @Autowired
    private DroneService service;
    //action para criação de 10 simulações de drone
    @PostMapping
    private ResponseEntity criarDroneSimulacao(){
        for(int cont=1;cont<=10;cont++)
        {
            service.criarDroneSimulacao(cont);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
