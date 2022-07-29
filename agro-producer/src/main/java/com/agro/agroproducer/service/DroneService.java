package com.agro.agroproducer.service;

import com.agro.agroproducer.job.DroneSimulacaoJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

@Service
public class DroneService {

    //Método que cria a simução de um drone
    public void criarDroneSimulacao(int droneId){
        try {
            // Cria job para simular um drone
            JobDetail job = JobBuilder.newJob(DroneSimulacaoJob.class)
                    .withIdentity("droneJob" + droneId)
                    .build();
            job.getJobDataMap().put("droneId",droneId);
            // Define periodo de tempo para execução do job
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(10)
                            .repeatForever())
                    .build();
            //Agenda o job
            SchedulerFactory schFactory = new StdSchedulerFactory();
            Scheduler sch = schFactory.getScheduler();
            sch.start();
            sch.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
