package com.agro.agroproducer.controller;

import com.agro.agroproducer.service.LeituraService;
import dto.LeituraDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/leitura")
public class LeituraController {
    
    @Autowired
    private LeituraService service;

    @PostMapping
    private ResponseEntity droneReading(@RequestBody LeituraDTO leituraDTO){

        if(this.service.sendMessage(leituraDTO)){
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
