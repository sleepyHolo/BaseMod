package basemod.patches.com.megacrit.cardcrawl.events.Vampires;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;

import java.util.stream.Collectors;

@SpirePatch(
		clz=Vampires.class,
		method="replaceAttacks"
)
public class ReplaceAttacks {
	public static void Prefix(Vampires __instance) {
		AbstractDungeon.player.masterDeck.group.stream()
				.filter(c -> {
					if (c instanceof CustomCard && ((CustomCard) c).isStrike()) {
						return true;
					} else if (c.hasTag(BaseModCardTags.BASIC_STRIKE)) {
						return true;
					}
					return false;
				})
				.collect(Collectors.toList())
				.forEach(c -> AbstractDungeon.player.masterDeck.removeCard(c));
	}
}
