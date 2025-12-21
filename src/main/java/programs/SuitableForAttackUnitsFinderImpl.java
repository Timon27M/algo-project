package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        List<Unit> resultUnits = new ArrayList();

        List<List<Unit>> sortedUnitsByRow = sortUnits(unitsByRow);

        for (int i = 0; i < 3; i++) {
            HashSet<Integer> yCoordinateUnits = getYCoordinateUnits(sortedUnitsByRow.get(i));
//            if (!sortedUnitsByRow.get(i).isEmpty()) {
//                resultUnits.add(sortedUnitsByRow.get(i).getFirst());
//            }

            for (Unit unit : sortedUnitsByRow.get(i)) {
                if (unit.getyCoordinate() == 0) {
                    resultUnits.add(unit);
                    continue;
                }

                int resultUnitsLength = resultUnits.size();

                if (resultUnits.get(resultUnitsLength - 1).getyCoordinate() != unit.getyCoordinate() - 1) {
                    resultUnits.add(unit);
                }
            }
        }

        return resultUnits;
    }

    private List<List<Unit>> sortUnits(List<List<Unit>> unitsByRow) {
        for (List<Unit> row : unitsByRow) {
            row.sort(Comparator.comparingInt(Unit::getyCoordinate));
        }

        return unitsByRow;
    }

    private HashSet<Integer> getYCoordinateUnits(List<Unit> units) {
        HashSet<Integer> yCoordinateUnits = new HashSet();

        for (Unit unit : units) {
            yCoordinateUnits.add(unit.getyCoordinate());
        }

        return yCoordinateUnits;
    }

}
