package basemod.patches.com.megacrit.cardcrawl.helpers.RelicLibrary;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.helpers.RelicLibrary", method="initialize")
public class EditRelicsPatch {

	public static void Prefix() {
		// have mods register their changes to the relic list here
		BaseMod.publishEditRelics();
	}
	
}
