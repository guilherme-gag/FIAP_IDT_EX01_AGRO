package com.agro.agroproducer.job;

import com.agro.agroproducer.controller.LeituraController;
import com.agro.agroproducer.service.LeituraService;
import com.agro.agroproducer.service.RabbitmqService;
import dto.LeituraDto;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class DroneSimulacaoJob implements Job {
    private RestTemplate restTemplate = new RestTemplate();
    public void execute(JobExecutionContext jExeCtx) throws JobExecutionException {
        JobDataMap dataMap = jExeCtx.getJobDetail().getJobDataMap();
        int droneId = dataMap.getInt("droneId");
        LeituraDto leitura = new LeituraDto();
        leitura.droneId = droneId;
        leitura.temperatura = getRandomNumber(-25,40);
        leitura.umidade=getRandomNumber(0,100);
        leitura.latitude = 100;
        leitura.longitude = 200;
        leitura.ativarRastreamento = false;
        //cria requisição http
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LeituraDto> httpExampleRequest = new HttpEntity<>(leitura, headers);
        HttpEntity response = restTemplate.postForObject("http://localhost:8080/leitura", httpExampleRequest, HttpEntity.class);
    }

    private int getRandomNumber (int min, int max) {

        int randomNumber = ( int )( Math.random() * max );
        if( randomNumber <= min ) {
            randomNumber = randomNumber + min;
        }
        return randomNumber;
    }
}
