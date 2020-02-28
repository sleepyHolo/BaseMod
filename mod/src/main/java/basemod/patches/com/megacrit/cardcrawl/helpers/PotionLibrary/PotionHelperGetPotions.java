package basemod.patches.com.megacrit.cardcrawl.helpers.PotionLibrary;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PotionHelper;

import java.util.ArrayList;

@SpirePatch(
		clz=PotionHelper.class,
		method="getPotions"
)
public class PotionHelperGetPotions
{
	public static ArrayList<String> Postfix(ArrayList<String> __result, AbstractPlayer.PlayerClass c, boolean getAll)
	{
		for (String potionID : BaseMod.getPotionIDs()) {
			AbstractPlayer.PlayerClass potionClass = BaseMod.getPotionPlayerClass(potionID);
			if (getAll || potionClass == null || potionClass == c) {
				__result.add(potionID);
			}
		}
		return __result;
	}
} 