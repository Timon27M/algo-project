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
                List<Unit> sortedPlayerUnits = sortedPlayerArmy.getUnits();
                List<Unit> sortedComputerUnits = sortedComputerArmy.getUnits();

                Unit playerUnit = sortedPlayerUnits.size() > playerCount ? sortedPlayerUnits.get(playerCount) : null;
                Unit computerUnit = sortedComputerUnits.size() > computerCount ? sortedComputerUnits.get(computerCount) : null;

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
        army.getUnits().sort((unit1, unit2) -> Integer.compare(unit2.getBaseAttack(), unit1.getBaseAttack()));

        System.out.println(army.getUnits());

        return army;
    }

    @Override
    public void printBattleLog(Unit unit, Unit unit1) {
        System.out.println("Атакующий: " + (unit != null ? unit.getName() : null));
        System.out.println("Цель атаки: " + (unit1 != null ? unit1.getName() : null));
    }
}