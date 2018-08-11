package basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterInfo;

import java.util.ArrayList;

@SpirePatch(
		cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon",
		method="populateMonsterList"
)
public class PopulateMonsterList
{
	private static int calls = 0;

	public static void Prefix(AbstractDungeon __instance, ArrayList<MonsterInfo> monsters, int numMonsters, boolean elites)
	{
		++calls;

		if (elites) {
			monsters.addAll(BaseMod.getEliteEncounters(AbstractDungeon.id));
		} else if (calls == 2) {
			monsters.addAll(BaseMod.getStrongMonsterEncounters(AbstractDungeon.id));
		} else {
			monsters.addAll(BaseMod.getMonsterEncounters(AbstractDungeon.id));
		}
		MonsterInfo.normalizeWeights(monsters);

		if (calls >= 3) {
			calls = 0;
		}
	}
}
