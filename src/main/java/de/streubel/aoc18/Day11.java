package de.streubel.aoc18;

import com.google.common.collect.Range;
import de.streubel.AdventOfCodeRunner;

import java.util.List;
import java.util.Objects;


public class Day11 extends AdventOfCodeRunner {

    private CellAdress cellAdressOfLargestPower;
    private int largestPower;
    private int maxSubgridSize;
    private Range<Integer> gridRange;

    public Day11() {
        this.gridRange = Range.closedOpen(1, 300);
    }

    @Override
    public void run(List<String> input) {
        int gridSerialNumber = Integer.parseInt(input.get(0));

        int[][] fuelGrid = new int[300][300];

        fillFuelGrid(fuelGrid, gridSerialNumber);

        largestPower = Integer.MIN_VALUE;
        maxSubgridSize = 0;
        for (int subgridSize = gridRange.lowerEndpoint(); subgridSize<gridRange.upperEndpoint(); subgridSize++) {
            int[][] packed = packSubgrid(fuelGrid, subgridSize);

            CellAdress cellAddress = findLargestGridAdress(packed, subgridSize);

            int[][] subgrid = extractSubgrid(fuelGrid, cellAddress, subgridSize);

            int x  = sum(subgrid);
            if (x > largestPower) {
                maxSubgridSize = subgridSize;
                largestPower = x;
                cellAdressOfLargestPower = cellAddress;
            }
        }

        System.out.println("largestPower = "+largestPower);
        System.out.println("cellAdressOfLargestPower = "+cellAdressOfLargestPower);
        System.out.println("maxSubgridSize = "+maxSubgridSize);
    }

    public CellAdress getCellAdressOfLargestPower() {
        return cellAdressOfLargestPower;
    }

    public int getLargestPower() {
        return largestPower;
    }

    public int getMaxSubgridSize() {
        return maxSubgridSize;
    }

    public void setGridRange(Range<Integer> gridRange) {
        this.gridRange = gridRange;
    }

    private int sum(int[][] grid) {
        int sum = 0;
        //noinspection ForLoopReplaceableByForEach
        for (int y=0; y<grid.length; y++) {
            for (int x=0; x<grid[y].length; x++) {
                sum += grid[y][x];
            }
        }
        return sum;
    }

    private int[][] extractSubgrid(int[][] fuelGrid, CellAdress cellAdress, int subgridSize) {
        int[][] subgrid = new int[subgridSize][subgridSize];
        for (int y=0; y<subgridSize; y++) {
            //noinspection ManualArrayCopy
            for (int x=0; x<subgridSize; x++) {
                subgrid[y][x] = fuelGrid[cellAdress.y-1+y][cellAdress.x-1+x];
            }
        }
        return subgrid;
    }

    private CellAdress findLargestGridAdress(int[][] packed, int subgridSize) {
        CellAdress cellAdress = null;
        int largestTotalPower = Integer.MIN_VALUE;
        for (int y=0; y<packed.length-subgridSize; y++) {
            for (int x=0; x<packed[y].length-subgridSize; x++) {
                if (packed[y][x] >= largestTotalPower) {
                    cellAdress = new CellAdress(x+1, y+1);
                    largestTotalPower = packed[y][x];
                }
            }
        }
        return cellAdress;
    }

    private int[][] packSubgrid(int[][] fuelGrid, int subgridSize) {
        int[][] packed = new int[fuelGrid.length][fuelGrid[0].length];
        for (int y=0; y<packed.length; y++) {
            for (int x=0; x<packed[y].length; x++) {
                packed[y][x] = sumSubgrid(fuelGrid, x, y, subgridSize);
            }
        }

        return packed;
    }

    private void fillFuelGrid(int[][] fuelGrid, int gridSerialNumber) {
        for (int y=0; y<fuelGrid.length; y++) {
            for (int x=0; x<fuelGrid[y].length; x++) {
                fuelGrid[y][x] = new CellAdress(x+1, y+1).getPowerLevel(gridSerialNumber);
            }
        }
    }

    private int sumSubgrid(int[][] fuelGrid, int x, int y, int subgridSize) {
        int sum = 0;
        for (int yy=y; yy<Math.min(y+subgridSize, fuelGrid.length); yy++) {
            for (int xx=x; xx<Math.min(x+subgridSize, fuelGrid[yy].length); xx++) {
                sum += fuelGrid[yy][xx];
            }
        }
        return sum;
    }

    public static class CellAdress {
        int x;
        int y;

        public CellAdress(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getPowerLevel(int gridSerialNumber) {
            int rackID = x + 10;
            //noinspection UnnecessaryLocalVariable
            int powerLevel = (rackID * (rackID * y + gridSerialNumber) / 100) % 10 - 5;
            return powerLevel;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CellAdress that = (CellAdress) o;
            return x == that.x &&
                    y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public String toString() {
            return ""+x+","+y;
        }
    }

}
