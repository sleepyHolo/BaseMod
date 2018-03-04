package basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon", method="onModifyPower")
public class PowersModified {

	public static void Postfix() {
		BaseMod.publishOnPowersModified();
	}
	
}
