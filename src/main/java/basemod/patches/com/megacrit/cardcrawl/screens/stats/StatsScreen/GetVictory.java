package basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.stats.CharStat;

@SpirePatch(
		cls="com.megacrit.cardcrawl.screens.stats.StatsScreen",
		method="getVictory"
)
public class GetVictory {
	public static int Postfix(int __result, AbstractPlayer.PlayerClass c)
	{
		if (BaseMod.isBaseGameCharacter(c)) {
			return __result;
		}

		CharStat stat = BaseMod.playerStatsMap.get(c);
		if (stat != null) {
			return stat.getVictoryCount();
		}
		return 0;
	}
}
