package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.ArrayList;
import java.util.List;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        List<Unit> suitableUnits = new ArrayList<>();

        for (List<Unit> row : unitsByRow) {
            int rowSize = row.size();
            if (rowSize == 0) {
                continue;
            }

            if (isLeftArmyTarget) {
                // атакуется левая армия → нужен юнит без соседа слева
                // первый в ряду всегда не закрыт слева
                suitableUnits.add(row.get(0));
            } else {
                // атакуется правая армия → нужен юнит без соседа справа
                // последний в ряду всегда не закрыт справа
                suitableUnits.add(row.get(rowSize - 1));
            }
        }

        return suitableUnits;
    }
}
