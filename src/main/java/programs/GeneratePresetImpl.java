package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {

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

        byte currentHeightCell = 0;
        byte currentWidthCell = 0;
        for (Unit template : unitList) {
            int count = 0;

            while (count < 11 && currentPoints >= template.getCost()) {
                if (currentPoints < template.getCost()) {
                    break;
                }
                if (currentHeightCell > 20 && currentWidthCell < 4) {
                    currentWidthCell += 1;
                    currentHeightCell = 0;
                } else if (currentWidthCell > 3) {
                    break;
                }
                Unit copy = new Unit(template.getUnitType() + " " + count, template.getUnitType(), template.getHealth(), template.getBaseAttack(), template.getCost(), template.getAttackType(), template.getAttackBonuses(), template.getDefenceBonuses(), currentWidthCell, currentHeightCell);

                result.add(copy);
                currentPoints -= template.getCost();
                count++;
                currentHeightCell += 1;
            }
        }

        Army army = new Army(result);
        army.setUnits(result);
        army.setPoints(maxPoints - currentPoints);
        return army;
    }
}
