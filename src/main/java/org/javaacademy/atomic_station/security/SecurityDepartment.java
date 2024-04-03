package org.javaacademy.atomic_station.security;

import lombok.Getter;
import org.javaacademy.atomic_station.NuclearStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * отдел безопасности
 */
@Component
public class SecurityDepartment {
    @Autowired
    @Lazy
    private NuclearStation nuclearStation;
    @Getter
    private int accidentCountPeriod;

    public void addAccident() {
        accidentCountPeriod += 1;
    }

    public void reset() {
        nuclearStation.incrementAccident(accidentCountPeriod);
        accidentCountPeriod = 0;
    }
}
