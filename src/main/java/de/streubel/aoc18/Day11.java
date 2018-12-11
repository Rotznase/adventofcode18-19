package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.util.List;
import java.util.Objects;


public class Day11 extends AdventOfCodeRunner {

    private CellAdress cellAdressOfLargestPower;
    private int largestPower;

    @Override
    public void run(List<String> input) {
        int gridSerialNumber = Integer.parseInt(input.get(0));

        int[][] fuelGrid = new int[300][300];

        fillFuelGrid(fuelGrid, gridSerialNumber);

        int[][] packed = pack33Grid(fuelGrid);

        cellAdressOfLargestPower = findLargestGridAdress(packed);

        int[][] subgrid = extractSubgrid(fuelGrid, cellAdressOfLargestPower);

        largestPower = sum(subgrid);

        System.out.println("largestPower="+largestPower);
        System.out.println("cellAdressOfLargestPower="+cellAdressOfLargestPower);
    }

    public CellAdress getCellAdressOfLargestPower() {
        return cellAdressOfLargestPower;
    }

    public int getLargestPower() {
        return largestPower;
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

    private int[][] extractSubgrid(int[][] fuelGrid, CellAdress cellAdress) {
        int[][] subgrid = new int[3][3];
        for (int y=0; y<3; y++) {
            //noinspection ManualArrayCopy
            for (int x=0; x<3; x++) {
                subgrid[y][x] = fuelGrid[cellAdress.y-1+y][cellAdress.x-1+x];
            }
        }
        return subgrid;
    }

    private CellAdress findLargestGridAdress(int[][] packed) {
        CellAdress cellAdress = null;
        int largestTotalPower = Integer.MIN_VALUE;
        for (int y=0; y<packed.length-3; y++) {
            for (int x=0; x<packed[y].length-3; x++) {
                if (packed[y][x] >= largestTotalPower) {
                    cellAdress = new CellAdress(x+1, y+1);
                    largestTotalPower = packed[y][x];
                }
            }
        }
        return cellAdress;
    }

    private int[][] pack33Grid(int[][] fuelGrid) {
        int[][] packed = new int[fuelGrid.length][fuelGrid[0].length];
        for (int y=0; y<packed.length; y++) {
            for (int x=0; x<packed[y].length; x++) {
                packed[y][x] = sum33Grid(fuelGrid, x, y);
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

    private int sum33Grid(int[][] fuelGrid, int x, int y) {
        int sum = 0;
        for (int yy=y; yy<Math.min(y+3, fuelGrid.length); yy++) {
            for (int xx=x; xx<Math.min(x+3, fuelGrid[yy].length); xx++) {
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
