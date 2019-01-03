package basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
@SpirePatch(
		clz = CardCrawlGame.class,
		method = "loadPostCombat",
		paramtypez = {
				SaveFile.class
		}
)
public class CustomRewardLoad
{
	@SuppressWarnings("unused")
	@SpireInsertPatch(
		locator = Locator.class,
		localvars = {"i"}
	)
	public static void Insert(CardCrawlGame __instance, SaveFile saveFile, RewardSave rewardSave)
	{
		if (BaseMod.customRewardTypeExists(RewardItem.RewardType.valueOf(rewardSave.type))) {
			AbstractDungeon.getCurrRoom().rewards.add(BaseMod.loadCustomRewardFromSave(rewardSave));
		}
	}

	public static class Locator extends SpireInsertLocator
	{
		@Override
		public int[] Locate(CtBehavior ctBehavior) throws Exception
		{
			Matcher matcher = new Matcher.MethodCallMatcher(Logger.class, "info");
			return LineFinder.findInOrder(ctBehavior, matcher);
		}
	}
}
