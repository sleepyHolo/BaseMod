package basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile;


import basemod.BaseMod;
import basemod.abstracts.CustomReward;
import com.evacipated.cardcrawl.modthespire.lib.*;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;

@SuppressWarnings("unused")
@SpirePatch(clz = SaveFile.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {SaveFile.SaveType.class})
public class CustomRewardSave {

	@SpireInsertPatch(locator = Locator.class)
	public static void Insert(SaveFile __instance, SaveFile.SaveType saveType) {
		for(RewardItem item : AbstractDungeon.getCurrRoom().rewards) {
			if(item instanceof CustomReward){
				CustomReward customReward = (CustomReward)item;
				if(BaseMod.customRewardTypeExists(customReward.type)){
					__instance.combat_rewards.add(BaseMod.saveCustomReward(customReward));
				}
			}
		}

	}

	public static class Locator extends SpireInsertLocator {
		@Override
		public int[] Locate(CtBehavior ctBehavior) throws Exception {
			Matcher matcher = new Matcher.FieldAccessMatcher(AbstractRoom.class, "rewards");
			return LineFinder.findInOrder(ctBehavior, matcher);
		}
	}
}
