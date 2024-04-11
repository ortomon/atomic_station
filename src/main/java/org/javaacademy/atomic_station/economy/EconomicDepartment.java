package org.javaacademy.atomic_station.economy;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

@Getter
public abstract class EconomicDepartment {
    @Value("${country.name}")
    private String countryName;
    @Value("${country.economic.currency}")
    private String currency;
    @Value("${country.economic.rate.basic}")
    protected BigDecimal basicRate;

    public abstract BigDecimal computeYearIncomes(long countElectricity);
}
