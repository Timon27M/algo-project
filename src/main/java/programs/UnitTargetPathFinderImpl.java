package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.programs.EdgeDistance;
import com.battle.heroes.army.programs.UnitTargetPathFinder;

import java.util.*;

public class UnitTargetPathFinderImpl implements UnitTargetPathFinder {
    private static final int WIDTH = 27;
    private static final int HEIGHT = 21;

    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };

    @Override
    public List<Edge> getTargetPath(Unit attackUnit,
                                    Unit targetUnit,
                                    List<Unit> existingUnitList) {

        int startX = attackUnit.getxCoordinate();
        int startY = attackUnit.getyCoordinate();

        int targetX = targetUnit.getxCoordinate();
        int targetY = targetUnit.getyCoordinate();

        boolean[][] blocked = buildBlockedMap(existingUnitList, targetX, targetY);

        int[][] distance = new int[WIDTH][HEIGHT];
        Edge[][] previous = new Edge[WIDTH][HEIGHT];

        for (int[] row : distance) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        PriorityQueue<EdgeDistance> queue =
                new PriorityQueue<>(Comparator.comparingInt(EdgeDistance::getDistance));

        distance[startX][startY] = 0;
        queue.add(new EdgeDistance(startX, startY, 0));

        while (!queue.isEmpty()) {
            EdgeDistance current = queue.poll();

            if (current.getX() == targetX && current.getY() == targetY) {
                break;
            }

            for (int[] dir : DIRECTIONS) {
                int nx = current.getX() + dir[0];
                int ny = current.getY() + dir[1];

                if (!isInside(nx, ny) || blocked[nx][ny]) {
                    continue;
                }

                int newDist = current.getDistance() + 1;

                if (newDist < distance[nx][ny]) {
                    distance[nx][ny] = newDist;
                    previous[nx][ny] = new Edge(current.getX(), current.getY());
                    queue.add(new EdgeDistance(nx, ny, newDist));
                }
            }
        }

        if (queue.poll() == null) {
            System.out.println("Unit не нашел путь для атаки");
            return new ArrayList();
        }

        return restorePath(previous, startX, startY, targetX, targetY);
    }

    private boolean[][] buildBlockedMap(List<Unit> units, int targetX, int targetY) {
        boolean[][] blocked = new boolean[WIDTH][HEIGHT];

        for (Unit unit : units) {
            if (!unit.isAlive()) {
                continue;
            }
            int x = unit.getxCoordinate();
            int y = unit.getyCoordinate();

            if (x == targetX && y == targetY) {
                continue;
            }

            blocked[x][y] = true;
        }
        return blocked;
    }

    private boolean isInside(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    private List<Edge> restorePath(Edge[][] previous,
                                   int startX, int startY,
                                   int targetX, int targetY) {

        List<Edge> path = new ArrayList<>();
        Edge current = previous[targetX][targetY];

        path.add(new Edge(targetX, targetY));
        while (current != null && !(current.getX() == startX && current.getY() == startY)) {

            path.add(current);
            current = previous[current.getX()][current.getY()];
        }

        if (current != null) {
            path.add(new Edge(startX, startY));
        }

        Collections.reverse(path);
        return path;
    }
}
