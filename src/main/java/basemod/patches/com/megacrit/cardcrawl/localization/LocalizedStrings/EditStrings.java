package basemod.patches.com.megacrit.cardcrawl.localization.LocalizedStrings;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.localization.LocalizedStrings", method="ctor")
public class EditStrings {
	public static void Postfix(Object __obj_instance) {
		BaseMod.publishEditStrings();
	}
}
