package com.egreat.movie.api.task;


import com.egreat.movie.api.service.UpdateMovieService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
@EnableScheduling
public class UpdateMovieInfoTask {

    @Resource
    private UpdateMovieService updateMovieService;

    @Scheduled(cron = "0 0 0 ? * 2,4,6")
    public void startUpdateMovieInfo() {
        updateMovieService.startUpdate();
    }
}
