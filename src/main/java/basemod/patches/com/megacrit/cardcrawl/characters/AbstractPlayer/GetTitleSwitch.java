package basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="getTitle")
public class GetTitleSwitch {

	public static Object Postfix(Object __original_ret, Object plyrClassObj) {
		AbstractPlayer.PlayerClass selection = (AbstractPlayer.PlayerClass) plyrClassObj;
		if (!selection.toString().equals("IRONCLAD") && !selection.toString().equals("THE_SILENT") &&
				!selection.toString().equals("CROWBOT")) {
			return (Object) BaseMod.getTitle(selection.toString());
		} else {
			return __original_ret;
		}
	}
	
}
