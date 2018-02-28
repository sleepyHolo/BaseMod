package basemod.patches.com.megacrit.cardcrawl.actions.common.ObtainPotionAction;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.actions.common.ObtainPotionAction", method="update")
public class ObtainPotionActionPotionGetHook {
	@SpireInsertPatch(rloc=18,localvars= {"potion"})
	public static void Insert(Object __obj_instance, Object potion) {
		AbstractPotion p = (AbstractPotion)potion;
		BaseMod.publishPotionGet(p);;
	}
}
