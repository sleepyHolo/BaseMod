package basemod.patches.com.megacrit.cardcrawl.helpers.CardLibrary;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.helpers.CardLibrary", method="initialize")
public class EditCardsPatch {

	public static void Prefix() {
		// have mods register their changes to the card list here
		BaseMod.publishEditCards();
	}
	
}
