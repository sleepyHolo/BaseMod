package basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import basemod.BaseMod;

public class ActChangeHooks {

	@SpirePatch(cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon", method="ctor")
	public static class InGameConstructor {
		
		public static void Postfix(Object __obj_instance,
				String name, String levelId, AbstractPlayer p, ArrayList<String> newSpecialOneTimeEventList) {
			if (levelId.equals("Exordium")) {
				BaseMod.publishStartGame();
			}
			BaseMod.publishStartAct();
		}
		
	}
	
	@SpirePatch(cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon", method="ctor",
			paramtypes={"java.lang.String", "com.megacrit.cardcrawl.characters.AbstractPlayer", "com.megacrit.cardcrawl.saveAndContinue.SaveFile"})
	public static class SavedGameConstructor {

		public static void Postfix(Object __obj_instance,
				String name, AbstractPlayer p, SaveFile saveFile) {
			BaseMod.publishStartGame();
		}
		
	}
	
}
