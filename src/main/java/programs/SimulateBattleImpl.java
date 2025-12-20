package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.*;

public class SimulateBattleImpl implements SimulateBattle, PrintBattleLog {
    PrintBattleLog printBattleLog;

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {
        Army sortedPlayerArmy = getSortedArmy(playerArmy);
        Army sortedComputerArmy = getSortedArmy(computerArmy);

        while (hasAliveUnits(sortedPlayerArmy) && hasAliveUnits(sortedComputerArmy)) {
            int playerCount = 0;
            int computerCount = 0;

            while (true) {
//                Map.Entry<Integer, Unit> playerUnitMap = getCurrentUnit(playerCount, sortedPlayerArmy.getUnits()).entrySet().iterator().next();
//                Map.Entry<Integer, Unit> computerUnitMap = getCurrentUnit(computerCount, sortedComputerArmy.getUnits()).entrySet().iterator().next();


//                Unit playerUnit = playerUnitMap.getValue();
//                Unit computerUnit = computerUnitMap.getValue();
                Unit playerUnit = sortedPlayerArmy.getUnits().size() > playerCount ? sortedPlayerArmy.getUnits().get(playerCount) : null;
                Unit computerUnit = sortedComputerArmy.getUnits().size() > computerCount ? sortedComputerArmy.getUnits().get(computerCount) : null;

//                playerCount = playerUnitMap.getKey();
//                computerCount = computerUnitMap.getKey();

                if (playerUnit == null && computerUnit == null) {
                    break;
                }

                if (playerUnit != null) {
                    Unit attackedUnit = playerUnit.getProgram().attack();
                    if (attackedUnit == null) {
                        break;
                    }
                    if (!attackedUnit.isAlive()) {
                        removeUnit(sortedComputerArmy.getUnits(), attackedUnit);
                    }
                    printBattleLog(playerUnit, attackedUnit);
                    playerCount++;
                }

                if (computerUnit != null) {
                    Unit attackedUnit = computerUnit.getProgram().attack();
                    if (attackedUnit == null) {
                        break;
                    }
                    if (!attackedUnit.isAlive()) {
                        removeUnit(sortedPlayerArmy.getUnits(), attackedUnit);
                    }
                    printBattleLog(computerUnit, attackedUnit);
                    computerCount++;
                }
            }
        }

    }

    private Map<Integer, Unit> getCurrentUnit(int count, List<Unit> units) {
        Map<Integer, Unit> unitMap = new HashMap<>();

        if (units.size() > count) {
            Unit unit = units.get(count);
            if (!unit.isAlive()) {
                units.remove(unit);
                int newCount = count + 1;
                unitMap = getCurrentUnit(newCount, units);
            } else {
                unitMap.put(count, unit);
            }
        } else {
            unitMap.put(count, null);
        }
        System.out.println(count);

        return unitMap;
    }

    private void removeUnit(List<Unit> units, Unit unit) {
        units.remove(unit);
    }

    private boolean hasAliveUnits(Army army) {
        for (Unit unit : army.getUnits()) {
            if (unit.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private Army getSortedArmy(Army army) {
        army.getUnits().sort((u1, u2) -> Integer.compare(u2.getBaseAttack(), u1.getBaseAttack()));

        return army;
    }

    @Override
    public void printBattleLog(Unit unit, Unit unit1) {
        System.out.println("Атакующий: " + (unit != null ? unit.getName() : "null"));
        System.out.println("Цель атаки: " + (unit1 != null ? unit1.getName() : "null"));
    }
}