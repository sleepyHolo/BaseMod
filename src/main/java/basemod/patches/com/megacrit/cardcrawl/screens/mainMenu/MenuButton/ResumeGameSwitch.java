package basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.MenuButton;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.screens.mainMenu.MenuButton", method="resumeGame")
public class ResumeGameSwitch {
	public static void Postfix(Object __obj_instance) {
		if (CardCrawlGame.chosenCharacter == null) {
			CardCrawlGame.chosenCharacter = BaseMod.getSaveClass();
		}
	}
}
