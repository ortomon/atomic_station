package org.javaacademy.atomic_station;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.atomic_station.economy.EconomicDepartment;
import org.javaacademy.atomic_station.exception.NuclearFuelIsEmptyException;
import org.javaacademy.atomic_station.exception.ReactorWorkException;
import org.javaacademy.atomic_station.security.SecurityDepartment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Slf4j
@RequiredArgsConstructor
@Component
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

    private void startYear() {
        log.info("Атомная станция начала работу.");
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
                try {
                    reactorDepartment.stop();
                } catch (ReactorWorkException e) {
                    log.warn("Внимание! Происходят работы на атомной станции! Электричества нет!");
                }
            }

            countEnergyGeneratedInYear += temp;
            dayCounter++;
        }

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
                """.formatted(countEnergyGeneratedInYear,
                        accidentCountPeriod,
                        accidentCountAllTime,
                        yearIncomes,
                        currency);
        log.info(result);
    }

    public void incrementAccident(int count) {
        accidentCountAllTime += count;
    }
}
