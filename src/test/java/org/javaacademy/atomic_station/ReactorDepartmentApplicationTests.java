package org.javaacademy.atomic_station;

import org.javaacademy.atomic_station.nuclearstation.ReactorDepartment;
import org.javaacademy.atomic_station.nuclearstation.exception.NuclearFuelIsEmptyException;
import org.javaacademy.atomic_station.nuclearstation.exception.ReactorWorkException;
import org.javaacademy.atomic_station.security.SecurityDepartment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("morocco")
class ReactorDepartmentApplicationTests {
    @Autowired
    private ReactorDepartment reactorDepartment;
    @MockBean
    private SecurityDepartment securityDepartment;

    @BeforeEach
    void resetReactor() {
        if (reactorDepartment.isWork()) {
            reactorDepartment.stop();
        }
    }

    @Test
    void runSuccess() {
        long result = reactorDepartment.run();
        assertEquals(result, ReactorDepartment.POWER_GENERATION_IN_ONE_DAY);
    }

    @Test
    void runFailReactorIsAlreadyWork() {
        reactorDepartment.run();
        assertThrows(ReactorWorkException.class, () -> reactorDepartment.run());
    }

    @Test
    void runFailIsHundredthRun() {
        reactorDepartment.countRunsReset();
        int counterRuns = 0;

        while (counterRuns < 99) {
            reactorDepartment.run();
            reactorDepartment.stop();
            counterRuns++;
        }

        assertThrows(NuclearFuelIsEmptyException.class, () -> reactorDepartment.run());
    }

    @Test
    void stopFailIsAlreadyStop() {
        assertThrows(ReactorWorkException.class, () -> reactorDepartment.stop());
    }
}
