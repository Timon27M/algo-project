package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.programs.UnitTargetPathFinder;

import java.util.*;

public class UnitTargetPathFinderImpl implements UnitTargetPathFinder {
    @Override
    public List<Edge> getTargetPath(Unit attackUnit, Unit targetUnit, List<Unit> existingUnitList) {
        // Ваше решение
        final int WIDTH = 27;
        final int HEIGHT = 21;

        boolean[][] blocked = new boolean[WIDTH][HEIGHT];

        // Помечаем клетки, занятые другими юнитами
        for (Unit unit : existingUnitList) {
            if (unit == attackUnit || unit == targetUnit) {
                continue;
            }
            blocked[unit.getxCoordinate()][unit.getyCoordinate()] = true;
        }

        int startX = attackUnit.getxCoordinate();
        int startY = attackUnit.getyCoordinate();
        int targetX = targetUnit.getxCoordinate();
        int targetY = targetUnit.getyCoordinate();

        boolean[][] visited = new boolean[WIDTH][HEIGHT];
        Edge[][] parent = new Edge[WIDTH][HEIGHT];

        Queue<Edge> queue = new ArrayDeque<>();
        queue.add(new Edge(startX, startY));
        visited[startX][startY] = true;

        // 8 направлений (включая диагонали)
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        while (!queue.isEmpty()) {
            Edge current = queue.poll();

            if (current.getX() == targetX && current.getY() == targetY) {
                break;
            }

            for (int i = 0; i < 8; i++) {
                int nx = current.getX() + dx[i];
                int ny = current.getY() + dy[i];

                if (nx < 0 || nx >= WIDTH || ny < 0 || ny >= HEIGHT) {
                    continue;
                }
                if (visited[nx][ny] || blocked[nx][ny]) {
                    continue;
                }

                visited[nx][ny] = true;
                parent[nx][ny] = current;
                queue.add(new Edge(nx, ny));
            }
        }

        // Если путь не найден
        if (!visited[targetX][targetY]) {
            return List.of();
        }

        // Восстановление кратчайшего пути
        List<Edge> path = new ArrayList<>();
        Edge step = new Edge(targetX, targetY);

        while (step != null) {
            path.add(step);
            step = parent[step.getX()][step.getY()];
        }

        Collections.reverse(path);
        return path;
    }
}
