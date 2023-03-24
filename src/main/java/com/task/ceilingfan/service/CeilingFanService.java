package com.task.ceilingfan.service;

import com.task.ceilingfan.entity.CeilingFan;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
@Service
public class CeilingFanService {

    public static final int CHECK_DAY_OFF_PERIOD = 3 * 1000;
    public static final int DAY = 25;
    public static final int MONTH = 12;

    @Setter
    @Getter
    private CeilingFan ceilingFan;

    public CeilingFanService() {
        this.ceilingFan = new CeilingFan();
    }

    @PostConstruct
    public void startTimerDayOff() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ceilingFan.isOn() && isDayOff()) {
                    ceilingFan.turnOff();
                    log.info("The ceiling fan turned off on a day off on a timer!");
                }
            }
        }, 0, CHECK_DAY_OFF_PERIOD);
    }

    public void increaseSpeed() {
        if (isDayOff()) {
            ceilingFan.turnOff();
            log.info("The ceiling fan off due to day off!");
            return;
        }
        if (ceilingFan.getSpeed() == CeilingFan.MAX_SPEED) {
            ceilingFan.turnOff();
            log.info("The ceiling fan off!");
        } else {
            ceilingFan.increaseSpeed();
            log.info("Speed increased!");
        }
    }

    public void reverseDirection() {
        if (isWorkingDay()) {
            if (CeilingFan.Direction.LEFT.equals(ceilingFan.getDirection())) {
                ceilingFan.setDirection(CeilingFan.Direction.RIGHT);
            } else {
                ceilingFan.setDirection(CeilingFan.Direction.LEFT);
            }
            log.info("Direction was changed!");
        }
    }

    private boolean isWorkingDay() {
        return !isDayOff();
    }

    private boolean isDayOff() {
        LocalDate today = LocalDate.now();
        return today.getMonthValue() == MONTH && today.getDayOfMonth() == DAY;
    }
}