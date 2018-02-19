package basemod.patches.com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.screens.charSelect.CharacterOption", method="updateHitbox")
public class UpdateHitboxBgImg {

	@SpireInsertPatch(rloc=49)
	public static void Insert(Object __obj_instance) {
		AbstractPlayer.PlayerClass chosenClass = CardCrawlGame.chosenCharacter;
		if (!chosenClass.toString().equals("IRONCLAD") && !chosenClass.toString().equals("THE_SILENT") &&
				!chosenClass.toString().equals("CROWBOT")) {
			CardCrawlGame.mainMenuScreen.charSelectScreen.bgCharImg =
					new Texture(BaseMod.getPlayerPortrait(chosenClass.toString()));
		}
	}
	
}
