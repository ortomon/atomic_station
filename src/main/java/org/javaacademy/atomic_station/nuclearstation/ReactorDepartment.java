package org.javaacademy.atomic_station.nuclearstation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javaacademy.atomic_station.nuclearstation.exception.NuclearFuelIsEmptyException;
import org.javaacademy.atomic_station.nuclearstation.exception.ReactorWorkException;
import org.javaacademy.atomic_station.security.SecurityDepartment;
import org.springframework.stereotype.Component;

/**
 * реакторный цех
 */
@Component
@RequiredArgsConstructor
@Getter
public class ReactorDepartment {
    public static final long POWER_GENERATION_IN_ONE_DAY = 10_000_000L;
    private boolean isWork;
    private int counterOfRuns = 0;
    private final SecurityDepartment securityDepartment;

    public long run() {
        counterOfRuns++;

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

    public void countRunsReset() {
        counterOfRuns = 0;
    }

    private boolean isHundredthRun() {
        return counterOfRuns % 100 == 0;
    }
}

