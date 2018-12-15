package basemod.patches.com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import basemod.abstracts.CustomCard;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.EverythingFix;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.lang.reflect.Field;

public class OpenFix
{
	@SpirePatch(
			clz=SingleCardViewPopup.class,
			method="open",
			paramtypez={
					AbstractCard.class,
					CardGroup.class
			}
	)
	public static class Open
	{
		public static void Prefix(SingleCardViewPopup __instance, AbstractCard card, @ByRef CardGroup[] group)
		{
			if (group[0] == null) {
				group[0] = EverythingFix.Fields.cardGroupMap.get(card.color);
			}
		}
	}

	@SpirePatch(
			clz=SingleCardViewPopup.class,
			method="loadPortraitImg"
	)
	public static class OpenTextureFix
	{
		public static void Postfix(SingleCardViewPopup __instance)
		{
			try {
				Field cardField = SingleCardViewPopup.class.getDeclaredField("card");
				cardField.setAccessible(true);
				AbstractCard card = (AbstractCard) cardField.get(__instance);

				Field portraitImageField = SingleCardViewPopup.class.getDeclaredField("portraitImg");
				portraitImageField.setAccessible(true);
				if (portraitImageField.get(__instance) == null && card instanceof CustomCard) {
					portraitImageField.set(__instance, CustomCard.getPortraitImage((CustomCard) card));
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}