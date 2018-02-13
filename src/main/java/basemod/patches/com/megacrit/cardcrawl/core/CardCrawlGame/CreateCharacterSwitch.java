package basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.core.CardCrawlGame", method="createCharacter")
public class CreateCharacterSwitch {

	@SpireInsertPatch(loc=999, localvars={"p"})
	public static void Insert(Object selectionObj, @ByRef Object[] pObj) {
		AbstractPlayer.PlayerClass selection = (AbstractPlayer.PlayerClass) selectionObj;
		AbstractPlayer p = (AbstractPlayer) pObj[0];
		if (!selection.toString().equals("IRONCLAD") && !selection.toString().equals("THE_SILENT") &&
				!selection.toString().equals("CROWBOT")) {
			p = BaseMod.createCharacter(selection.toString(), CardCrawlGame.playerName);
			pObj[0] = p;
		}
	}
	
}
