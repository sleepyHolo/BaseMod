package basemod.patches.com.megacrit.cardcrawl.core.AbstractCreature;

import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.actions.GameActionManager.OnPlayerLoseBlockToggle;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;

@SpirePatch(
		clz= AbstractCreature.class,
		method="loseBlock",
		paramtypez= {
				int.class,
				boolean.class
		})
public class ModifyPlayerLoseBlock
{
	@SpireInsertPatch(
			rloc = 0,
			localvars = {"amount"}
	)
	public static void Insert(AbstractCreature __instance, int amount, boolean noAnimation, @ByRef int[] modifyAmount){
		if (OnPlayerLoseBlockToggle.on) {
			modifyAmount[0] = BaseMod.publishOnPlayerLoseBlock(amount);
		}
		if (modifyAmount[0] < 0) {
			modifyAmount[0] = 0;
		}
	}
}
