package basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="getTitle")
public class GetTitleSwitch {

	public static Object Postfix(Object __original_ret, Object plyrClassObj) {
		AbstractPlayer.PlayerClass selection = (AbstractPlayer.PlayerClass) plyrClassObj;
		if (selection != AbstractPlayer.PlayerClass.IRONCLAD && selection != AbstractPlayer.PlayerClass.THE_SILENT &&
				selection != AbstractPlayer.PlayerClass.DEFECT) {
			return BaseMod.getTitle(selection.toString());
		} else {
			return __original_ret;
		}
	}
	
}
