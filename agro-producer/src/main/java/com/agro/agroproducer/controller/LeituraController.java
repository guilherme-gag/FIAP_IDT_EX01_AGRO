package com.agro.agroproducer.controller;

import com.agro.agroproducer.service.LeituraService;
import dto.LeituraDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "leitura")
public class LeituraController {
    @Autowired
    private LeituraService service;

    @PostMapping
    private ResponseEntity insereLeitura(@RequestBody LeituraDto leituraDto){
        System.out.println(leituraDto.droneId);
        if(this.service.enviarMensagem(leituraDto))
        return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }
}
