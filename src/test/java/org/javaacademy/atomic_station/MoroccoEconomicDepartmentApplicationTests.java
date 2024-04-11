package org.javaacademy.atomic_station;

import org.javaacademy.atomic_station.economy.FranceEconomicDepartment;
import org.javaacademy.atomic_station.economy.MoroccoEconomicDepartment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.crypto.Mac;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("morocco")
class MoroccoEconomicDepartmentApplicationTests {
	@Autowired
	private MoroccoEconomicDepartment moroccoEconomicDepartment;

	@Test
	void computeYearIncomesSuccess() {
		BigDecimal result = moroccoEconomicDepartment.computeYearIncomes(6_000_000_000L);
		BigDecimal expected = valueOf(5_000_000_000L).multiply(valueOf(5))
				.add(valueOf(1_000_000_000L).multiply(valueOf(6)));
		assertEquals(expected, result);
	}
}