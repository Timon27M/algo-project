package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.*;

public class SimulateBattleImpl implements SimulateBattle {
    private PrintBattleLog printBattleLog;

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {


        while (hasAliveUnits(playerArmy) && hasAliveUnits(computerArmy)) {

            // 1. Формируем список всех живых юнитов
            List<Unit> turnQueue = new ArrayList<>();

            for (Unit unit : playerArmy.getUnits()) {
                if (unit.isAlive()) {
                    turnQueue.add(unit);
                }
            }

            for (Unit unit : computerArmy.getUnits()) {
                if (unit.isAlive()) {
                    turnQueue.add(unit);
                }
            }

            // 2. Сортируем по убыванию базовой атаки
            turnQueue.sort(
                    (u1, u2) -> Integer.compare(u2.getBaseAttack(), u1.getBaseAttack())
            );

            // 3. Юниты совершают ходы
            for (Unit attacker : turnQueue) {

                // Юнит мог погибнуть до своего хода
                if (!attacker.isAlive()) {
                    continue;
                }

                // Проверка окончания боя
                if (!hasAliveUnits(playerArmy) || !hasAliveUnits(computerArmy)) {
                    return;
                }

                Unit target = attacker.getProgram().attack();

                if (target != null) {
                    this.printBattleLog.printBattleLog(attacker, target);
                }
            }
            // Раунд завершён → начинается следующий
        }
    }

    private boolean hasAliveUnits(Army army) {
        for (Unit unit : army.getUnits()) {
            if (unit.isAlive()) {
                return true;
            }
        }
        return false;
    }
}