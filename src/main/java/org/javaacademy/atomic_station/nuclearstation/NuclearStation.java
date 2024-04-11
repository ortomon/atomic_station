package org.javaacademy.atomic_station.nuclearstation;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.atomic_station.economy.EconomicDepartment;
import org.javaacademy.atomic_station.nuclearstation.exception.NuclearFuelIsEmptyException;
import org.javaacademy.atomic_station.nuclearstation.exception.ReactorWorkException;
import org.javaacademy.atomic_station.security.SecurityDepartment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Component
@Getter
public class NuclearStation {
    private static final int DAYS_YEAR = 365;
    @NonNull
    private final ReactorDepartment reactorDepartment;
    @NonNull
    private final SecurityDepartment securityDepartment;
    @NonNull
    private final EconomicDepartment economicDepartment;
    private long totalCountEnergyGenerated;
    private int accidentCountAllTime;

    public void start(int year) {
        log.info("Действие происходит в стране: {}", economicDepartment.getCountryName());

        for (int i = 0; i < year; i++) {
            startYear();
        }
    }

    public void incrementAccident(int count) {
        accidentCountAllTime += count;
    }

    private void startYear() {
        log.info("Атомная станция начала работу.");
        long countEnergyGeneratedInYear = runReactor();
        totalCountEnergyGenerated += countEnergyGeneratedInYear;
        BigDecimal yearIncomes = economicDepartment.computeYearIncomes(countEnergyGeneratedInYear);
        String currency = economicDepartment.getCurrency();
        int accidentCountPeriod = securityDepartment.getAccidentCountPeriod();
        securityDepartment.reset();
        String result =
                """
                Атомная станция закончила работу. За год Выработано %s киловатт/часов.
                Количество инцидентов за год: %s
                Количество инцидентов за всю работу станции: %s
                Доход за год составил %s %s
                """
                        .formatted(countEnergyGeneratedInYear,
                        accidentCountPeriod,
                        accidentCountAllTime,
                        yearIncomes,
                        currency);
        log.info(result);
    }

    private long runReactor() {
        int dayCounter = 0;
        long countEnergyGeneratedInYear = 0L;

        while (dayCounter < DAYS_YEAR) {
            long temp;

            try {
                temp = reactorDepartment.run();
            } catch (ReactorWorkException | NuclearFuelIsEmptyException e) {
                temp = 0L;
                log.warn("Внимание! Происходят работы на атомной станции! Электричества нет!");
            } finally {
                stopReactor();
            }

            countEnergyGeneratedInYear += temp;
            dayCounter++;
        }

        return countEnergyGeneratedInYear;
    }

    private void stopReactor() {
        try {
            reactorDepartment.stop();
        } catch (ReactorWorkException e) {
            log.warn("Внимание! Происходят работы на атомной станции! Электричества нет!");
        }
    }
}
