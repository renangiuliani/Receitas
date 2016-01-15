package com.example.renan.recipeapplication.entities;

import java.util.Timer;

/**
 * Created by c1284141 on 29/12/2015.
 */
public class AdditionalTimer {

    private String description;
    private String timerStr;
    private Boolean activeTimer;
    Timer timer;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimerStr() {
        return timerStr;
    }

    public void setTimerStr(String timerStr) {
        this.timerStr = timerStr;
    }

    public Boolean getActiveTimer() {
        return activeTimer;
    }

    public void setActiveTimer(Boolean activeTimer) {
        this.activeTimer = activeTimer;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

}
