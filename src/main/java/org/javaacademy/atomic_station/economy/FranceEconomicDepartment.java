package org.javaacademy.atomic_station.economy;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;

@Profile("france")
@Component
public class FranceEconomicDepartment extends EconomicDepartment {
    private static final BigDecimal DISCOUNT_RATE = valueOf(0.01);
    private static final long COUNT_ELECTRICITY_BASIC_RATE = 1_000_000_000L;

    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        BigDecimal totalIncome = ZERO;
        BigDecimal rate = basicRate;

        for (int i = 1; i <= countElectricity / COUNT_ELECTRICITY_BASIC_RATE; i++) {
            totalIncome = totalIncome.add(rate.multiply(valueOf(COUNT_ELECTRICITY_BASIC_RATE)));
            rate = rate.multiply(ONE.subtract(DISCOUNT_RATE));
        }

        long remainder = countElectricity % COUNT_ELECTRICITY_BASIC_RATE;
        totalIncome = totalIncome.add(rate.multiply(valueOf(remainder)));
        return totalIncome;
    }
}