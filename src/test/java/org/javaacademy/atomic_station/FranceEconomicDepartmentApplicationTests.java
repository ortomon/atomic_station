package org.javaacademy.atomic_station;

import org.javaacademy.atomic_station.economy.FranceEconomicDepartment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("france")
class FranceEconomicDepartmentApplicationTests {
	@Autowired
	private FranceEconomicDepartment franceEconomicDepartment;

	@Test
	void computeYearIncomesSuccess() {
		BigDecimal result = franceEconomicDepartment.computeYearIncomes(3_000_000_000L);
		BigDecimal billion = valueOf(1_000_000_000L);
		BigDecimal basicRate = valueOf(0.5);
		BigDecimal discountRate = valueOf(0.01);

		BigDecimal expected = billion.multiply(basicRate)
				.add(billion.multiply(basicRate.multiply(ONE.subtract(discountRate))))
				.add(billion.multiply(basicRate.multiply(ONE.subtract(discountRate)).multiply(ONE.subtract(discountRate))));
		expected = expected.setScale(7, RoundingMode.HALF_UP);
		assertEquals(expected, result);
	}
}

