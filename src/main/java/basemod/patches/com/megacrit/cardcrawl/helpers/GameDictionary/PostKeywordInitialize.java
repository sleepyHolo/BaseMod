package basemod.patches.com.megacrit.cardcrawl.helpers.GameDictionary;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.helpers.GameDictionary", method="initialize")
public class PostKeywordInitialize {

	public static void Postfix() {
		BaseMod.publishEditKeywords();
	}
	
}
