package org.javaacademy.atomic_station;

import org.javaacademy.atomic_station.exception.NuclearFuelIsEmptyException;
import org.javaacademy.atomic_station.exception.ReactorWorkException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ReactorDepartment {
    private static final BigDecimal POWER_GENERATION_IN_ONE_DAY = BigDecimal.valueOf(10_000_000);
    private boolean isWork;
    private int counter = 0;

    public BigDecimal run() {
        counter++;

        if (isWork) {
            throw new ReactorWorkException("Реактор уже работает");
        }

        if (isHundredthStart()) {
            throw new NuclearFuelIsEmptyException();
        }

        isWork = true;
        return POWER_GENERATION_IN_ONE_DAY;
    }

    public void stop() {
        if (!isWork) {
            throw new ReactorWorkException("Реактор уже выключен");
        }

        isWork = false;
    }

    private boolean isHundredthStart() {
        return counter % 100 == 0;
    }
}

