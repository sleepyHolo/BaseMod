package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
		clz=AbstractCard.class,
		method="displayUpgrades"
)
public class SmithPreview
{
	public static void Postfix(AbstractCard __instance)
	{
		for (DynamicVariable dynamicVariable : BaseMod.cardDynamicVariableMap.values()) {
			if (dynamicVariable.upgraded(__instance)) {
				dynamicVariable.setIsModified(__instance, true);
			}
		}
	}
}
