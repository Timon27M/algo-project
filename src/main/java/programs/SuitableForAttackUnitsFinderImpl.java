package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        List<Unit> resultUnits = new ArrayList();

        for (int i = 0; i < 3; i++) {
            HashSet<Integer> yCoordinateUnits = getYCoordinateUnits(unitsByRow.get(i));

            for (Unit unit : unitsByRow.get(i)) {
                if (unit.getyCoordinate() == 0) {
                    resultUnits.add(unit);
                    continue;
                }

                if (!yCoordinateUnits.contains(unit.getyCoordinate() - 1)) {
                    resultUnits.add(unit);
                }
            }
        }

        return resultUnits;
    }

    private HashSet<Integer> getYCoordinateUnits(List<Unit> units) {
        HashSet<Integer> yCoordinateUnits = new HashSet();

        for (Unit unit : units) {
            yCoordinateUnits.add(unit.getyCoordinate());
        }

        return yCoordinateUnits;
    }

}
