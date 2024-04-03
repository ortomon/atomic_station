package org.javaacademy.atomic_station;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.atomic_station.exception.NuclearFuelIsEmptyException;
import org.javaacademy.atomic_station.exception.ReactorWorkException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Slf4j
@RequiredArgsConstructor
@Component
public class NuclearStation {
    private static final int DAYS_YEAR = 365;
    private final ReactorDepartment reactorDepartment;
    private BigDecimal totalCountEnergyGenerated = ZERO;

    public void start(int year) {
        for (int i = 0; i < year; i++) {
            startYear();
        }
    }

    private void startYear() {
        log.info("Атомная станция начала работу.");
        int dayCounter = 0;
        BigDecimal countEnergyGenerated = ZERO;

        while (dayCounter < DAYS_YEAR) {
            BigDecimal temp;

            try {
                temp = reactorDepartment.run();
            } catch (ReactorWorkException | NuclearFuelIsEmptyException e) {
                temp = ZERO;
                log.warn("Внимание! Происходят работы на атомной станции! Электричества нет!");
            } finally {
                try {
                    reactorDepartment.stop();
                } catch (ReactorWorkException e) {
                    log.warn("Внимание! Происходят работы на атомной станции! Электричества нет!");
                }
            }

            countEnergyGenerated = countEnergyGenerated.add(temp);
            dayCounter++;
        }

        totalCountEnergyGenerated = totalCountEnergyGenerated.add(countEnergyGenerated);
        log.info("Атомная станция закончила работу. За год Выработано {} киловатт/часов", countEnergyGenerated);
    }
}