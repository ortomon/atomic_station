package org.javaacademy.atomic_station;

import lombok.NonNull;
import org.javaacademy.atomic_station.economy.EconomicDepartment;
import org.javaacademy.atomic_station.nuclearstation.NuclearStation;
import org.javaacademy.atomic_station.nuclearstation.ReactorDepartment;
import org.javaacademy.atomic_station.security.SecurityDepartment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ActiveProfiles("morocco")
public class NuclearStationApplicationTests {
    @Autowired
    private NuclearStation nuclearStation;
    @Autowired
    private ReactorDepartment reactorDepartment;
    @MockBean
    private SecurityDepartment securityDepartment;
    @MockBean
    private EconomicDepartment economicDepartment;

    @Test
    void startSuccess() {
        assertDoesNotThrow(() -> nuclearStation.start(1));
        assertNotEquals(0L, nuclearStation.getTotalCountEnergyGenerated());
    }

    @Test
    void incrementAccidentSuccess() {
        nuclearStation.incrementAccident(1);
        Assertions.assertEquals(1, nuclearStation.getAccidentCountAllTime());
    }
}
