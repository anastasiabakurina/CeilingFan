package com.task.ceilingfan;

import com.task.ceilingfan.entity.CeilingFan;
import com.task.ceilingfan.service.CeilingFanService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CeilingFanServiceTest {

    @Resource
    private CeilingFanService ceilingFanService;

    @Before
    public void init() {
        ceilingFanService.getCeilingFan().turnOff();
    }

    @Test
    public void testIncreaseSpeed() {
        CeilingFan ceilingFan = ceilingFanService.getCeilingFan();

        assertEquals(0, ceilingFan.getSpeed());
        ceilingFanService.increaseSpeed();
        assertEquals(1, ceilingFan.getSpeed());

        ceilingFanService.increaseSpeed();
        assertEquals(2, ceilingFan.getSpeed());

        ceilingFanService.increaseSpeed();
        assertEquals(3, ceilingFan.getSpeed());

        ceilingFanService.increaseSpeed();
        assertTrue(ceilingFan.isOff());

        ceilingFanService.increaseSpeed();
        assertEquals(1, ceilingFan.getSpeed());

        ceilingFanService.increaseSpeed();
        ceilingFanService.increaseSpeed();
        assertEquals(3, ceilingFan.getSpeed());
    }

    @Test
    public void testReverseDirection() {
        CeilingFan ceilingFan = ceilingFanService.getCeilingFan();
        assertEquals(CeilingFan.Direction.LEFT, ceilingFan.getDirection());

        ceilingFanService.reverseDirection();
        assertEquals(CeilingFan.Direction.RIGHT, ceilingFan.getDirection());

        ceilingFanService.reverseDirection();
        assertEquals(CeilingFan.Direction.LEFT, ceilingFan.getDirection());

        ceilingFanService.reverseDirection();
        ceilingFanService.reverseDirection();
        assertEquals(CeilingFan.Direction.LEFT, ceilingFan.getDirection());
    }

    @Test
    public void testIsDayOff() {
        LocalDate dayOff = LocalDate.of(2023, 12, 25);

        try (MockedStatic<LocalDate> localDateMockedStatic = Mockito.mockStatic(LocalDate.class)) {
            localDateMockedStatic.when(LocalDate::now).thenReturn(dayOff);

            CeilingFan ceilingFan = ceilingFanService.getCeilingFan();
            ceilingFanService.increaseSpeed();
            assertEquals(0, ceilingFan.getSpeed());

            ceilingFanService.increaseSpeed();
            ceilingFanService.increaseSpeed();
            assertEquals(0, ceilingFan.getSpeed());
        }
    }
}