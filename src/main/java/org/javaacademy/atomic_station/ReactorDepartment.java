package org.javaacademy.atomic_station;

import lombok.RequiredArgsConstructor;
import org.javaacademy.atomic_station.exception.NuclearFuelIsEmptyException;
import org.javaacademy.atomic_station.exception.ReactorWorkException;
import org.javaacademy.atomic_station.security.SecurityDepartment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * реакторный цех
 */
@Component
@RequiredArgsConstructor
public class ReactorDepartment {
    private static final BigDecimal POWER_GENERATION_IN_ONE_DAY = BigDecimal.valueOf(10_000_000);
    private boolean isWork;
    private int counter = 0;
    private final SecurityDepartment securityDepartment;

    public BigDecimal run() {
        counter++;

        if (isWork) {
            securityDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже работает");
        }

        if (isHundredthRun()) {
            securityDepartment.addAccident();
            throw new NuclearFuelIsEmptyException();
        }

        isWork = true;
        return POWER_GENERATION_IN_ONE_DAY;
    }

    public void stop() {
        if (!isWork) {
            securityDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже выключен");
        }

        isWork = false;
    }

    private boolean isHundredthRun() {
        return counter % 100 == 0;
    }
}

