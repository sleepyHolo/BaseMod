package basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.core.CardCrawlGame", method="createCharacter")
public class CreateCharacterSwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	@SpireInsertPatch(rloc=16, localvars={"p"})
	public static void Insert(Object selectionObj, @ByRef(type="com.megacrit.cardcrawl.characters.AbstractPlayer") Object[] pObj) {
		logger.info("hooking into character creation");
		
		AbstractPlayer.PlayerClass selection = (AbstractPlayer.PlayerClass) selectionObj;
		AbstractPlayer p;
		if (!selection.toString().equals("IRONCLAD") && !selection.toString().equals("THE_SILENT") &&
				!selection.toString().equals("CROWBOT")) {
			logger.info("creating character " + selection.toString());
			p = BaseMod.createCharacter(selection.toString(), CardCrawlGame.playerName);
			pObj[0] = p;
		}
	}
	
}
