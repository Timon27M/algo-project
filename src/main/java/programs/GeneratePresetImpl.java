package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {

    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {
        // Ваше решение
        unitList.sort((u1, u2) -> {
            double e1 = (double) u1.getBaseAttack() / u1.getCost();
            double e2 = (double) u2.getBaseAttack() / u2.getCost();

            if (Double.compare(e2, e1) != 0) {
                return Double.compare(e2, e1);
            }

            double h1 = (double) u1.getHealth() / u1.getCost();
            double h2 = (double) u2.getHealth() / u2.getCost();
            return Double.compare(h2, h1);
        });

        List<Unit> result = new ArrayList<>();
        int pointsLeft = maxPoints;

        for (Unit template : unitList) {
            int count = 0;

            while (count < 11 && pointsLeft >= template.getCost()) {
                Unit copy = new Unit(template.getName(), template.getUnitType(), template.getHealth(), template.getBaseAttack(), template.getCost(), template.getAttackType(), template.getAttackBonuses(), template.getDefenceBonuses(), template.getxCoordinate(), template.getyCoordinate());
                copy.setName(template.getUnitType() + "_" + count);

                result.add(copy);
                pointsLeft -= template.getCost();
                count++;
            }
        }

        Army army = new Army();
        army.setUnits(result);
        army.setPoints(maxPoints - pointsLeft);
        return army;
    }
}
