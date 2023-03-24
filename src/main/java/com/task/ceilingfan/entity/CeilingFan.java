package com.task.ceilingfan.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CeilingFan {

    public static final int MAX_SPEED = 3;

    @EqualsAndHashCode.Include
    private Direction direction = Direction.LEFT;
    private int speed = 0;

    public void increaseSpeed() {
        this.speed++;
    }

    public boolean isOff() {
        return speed == 0;
    }

    public boolean isOn() {
        return !isOff();
    }

    public void turnOff() {
        this.speed = 0;
    }

    public enum Direction {LEFT, RIGHT}
}