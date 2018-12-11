package de.streubel;

import com.google.common.collect.Range;
import de.streubel.aoc18.Day11;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class Day11Test {

    @Test
    public void testPowerLevel() {
        assertEquals( 4, new Day11.CellAdress(3, 5).getPowerLevel(8));
        assertEquals(-5, new Day11.CellAdress(122,79).getPowerLevel(57));
        assertEquals( 0, new Day11.CellAdress(217,196).getPowerLevel(39));
        assertEquals( 4, new Day11.CellAdress(101,153).getPowerLevel(71));
    }

    @Test
    public void testTotalPower18() {
        Day11 day11 = new Day11();
        day11.setGridRange(Range.closedOpen(3,4));
        day11.run(Collections.singletonList("18"));
        assertEquals(new Day11.CellAdress(33, 45), day11.getCellAdressOfLargestPower());
        assertEquals(29, day11.getLargestPower());
    }

    @Test
    public void testTotalPower42() {
        Day11 day11 = new Day11();
        day11.setGridRange(Range.closedOpen(3,4));
        day11.run(Collections.singletonList("42"));
        assertEquals(new Day11.CellAdress(21, 61), day11.getCellAdressOfLargestPower());
        assertEquals(30, day11.getLargestPower());
    }

    @Test
    public void testTotalPowerOpenGrid18() {
        Day11 day11 = new Day11();
        day11.setGridRange(Range.closedOpen(1,300));
        day11.run(Collections.singletonList("18"));
        assertEquals(new Day11.CellAdress(90,269), day11.getCellAdressOfLargestPower());
        assertEquals(16, day11.getMaxSubgridSize());
    }

    @Test
    public void testTotalPowerOpenGrid42() {
        Day11 day11 = new Day11();
        day11.setGridRange(Range.closedOpen(1,300));
        day11.run(Collections.singletonList("42"));
        assertEquals(new Day11.CellAdress(232,251), day11.getCellAdressOfLargestPower());
        assertEquals(12, day11.getMaxSubgridSize());
    }
}
