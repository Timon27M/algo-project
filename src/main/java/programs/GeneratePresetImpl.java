package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {
    private final Random random = new Random();
    private HashSet<Coordinate> coordinates = new HashSet<Coordinate>();

    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {
        // Ваше решение
        unitList.sort((unit1, unit2) -> {
            double baseAttack1 = (double) unit1.getBaseAttack() / unit1.getCost();
            double baseAttack2 = (double) unit2.getBaseAttack() / unit2.getCost();

            if (Double.compare(baseAttack2, baseAttack1) != 0) {
                return Double.compare(baseAttack2, baseAttack1);
            }

            double health1 = (double) unit1.getHealth() / unit1.getCost();
            double health2 = (double) unit2.getHealth() / unit2.getCost();
            return Double.compare(health2, health1);
        });

        List<Unit> result = new ArrayList<>();
        int currentPoints = maxPoints;

        for (Unit template : unitList) {
            int count = 0;

            while (count < 11 && currentPoints >= template.getCost()) {
                Coordinate coordinate;
                do {
                    coordinate = new Coordinate(
                            random.nextInt(3),
                            random.nextInt(21)
                    );
                } while (coordinates.contains(coordinate));

                coordinates.add(coordinate);

                Unit copy = new Unit(template.getUnitType() + " " + count, template.getUnitType(), template.getHealth(), template.getBaseAttack(), template.getCost(), template.getAttackType(), template.getAttackBonuses(), template.getDefenceBonuses(), coordinate.getX(), coordinate.getY());

                result.add(copy);
                currentPoints -= template.getCost();
                count++;
            }
        }

        Army army = new Army(result);
        army.setUnits(result);
        army.setPoints(maxPoints - currentPoints);
        return army;
    }

    private static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }


        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coordinate c)) return false;
            return x == c.x && y == c.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }
}
