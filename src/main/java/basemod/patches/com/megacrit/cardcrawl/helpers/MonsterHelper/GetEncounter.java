package basemod.patches.com.megacrit.cardcrawl.helpers.MonsterHelper;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

@SpirePatch(
		cls="com.megacrit.cardcrawl.helpers.MonsterHelper",
		method="getEncounter"
)
public class GetEncounter
{
	public static MonsterGroup Postfix(MonsterGroup __result, String key)
	{
		if (BaseMod.customMonsterEncounterExists(key)) {
			return BaseMod.getMonsterEncounter(key);
		}
		return __result;
	}
}
