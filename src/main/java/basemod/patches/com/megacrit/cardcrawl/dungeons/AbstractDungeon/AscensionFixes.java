package basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
		cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon",
		method="dungeonTransitionSetup"
)
public class AscensionFixes
{
	@SpireInsertPatch(
			rloc=43
	)
	public static void Insert()
	{
		if (!BaseMod.isBaseGameCharacter(AbstractDungeon.player.chosenClass)) {
			AbstractDungeon.player.decreaseMaxHealth((int)(AbstractDungeon.player.maxHealth * 0.0625f));
		}
	}
}
