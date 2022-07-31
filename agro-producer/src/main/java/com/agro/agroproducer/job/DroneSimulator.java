package com.agro.agroproducer.job;

import com.agro.agroproducer.service.DroneService;
import com.agro.agroproducer.service.LeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.timer.Timer;

@RestController
@RequestMapping(value = "drone")
public class DroneSimulator {

    @Autowired
    private DroneService service;

    @Scheduled(fixedRate = Timer.ONE_SECOND * 30, initialDelay = Timer.ONE_SECOND * 10)
    private void droneSimulator() {
        service.criarDroneSimulacao();
    }
}
