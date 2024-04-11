package org.javaacademy.atomic_station;

import org.javaacademy.atomic_station.nuclearstation.NuclearStation;
import org.javaacademy.atomic_station.security.SecurityDepartment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("morocco")
class SecurityDepartmentApplicationTests {
	@Autowired
	private SecurityDepartment securityDepartment;
	@MockBean
	private NuclearStation nuclearStation;

	@Test
	void addAccidentSuccess() {
		securityDepartment.addAccident();
		assertEquals(1, securityDepartment.getAccidentCountPeriod());
	}

	@Test
	void resetSuccess() {
		securityDepartment.reset();
		assertEquals(0, securityDepartment.getAccidentCountPeriod());
	}
}
