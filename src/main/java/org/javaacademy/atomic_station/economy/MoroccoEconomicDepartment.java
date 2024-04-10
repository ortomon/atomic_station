package org.javaacademy.atomic_station.economy;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Profile("morocco")
@Component
public class MoroccoEconomicDepartment extends EconomicDepartment {
    private static final long COUNT_ELECTRICITY_BASIC_RATE = 5_000_000_000L;
    @Value("${country.economic.rate.higher}")
    private BigDecimal higherRate;

    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        BigDecimal totalIncome;

        if (countElectricity > COUNT_ELECTRICITY_BASIC_RATE) {
            long remainder = countElectricity - COUNT_ELECTRICITY_BASIC_RATE;
            totalIncome = BigDecimal.valueOf(COUNT_ELECTRICITY_BASIC_RATE)
                    .multiply(basicRate)
                    .add(BigDecimal.valueOf(remainder).multiply(higherRate));
        } else {
            totalIncome = BigDecimal.valueOf(COUNT_ELECTRICITY_BASIC_RATE).multiply(basicRate);
        }

        return totalIncome;
    }
}