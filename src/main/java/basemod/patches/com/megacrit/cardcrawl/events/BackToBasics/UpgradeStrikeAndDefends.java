package basemod.patches.com.megacrit.cardcrawl.events.BackToBasics;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import basemod.abstracts.CustomCard;

@SpirePatch(
		clz=BackToBasics.class,
		method="upgradeStrikeAndDefends"
)
public class UpgradeStrikeAndDefends {
	@SpireInsertPatch(rloc = 30)
	public static void Insert(Object __obj_instance) {
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
			if (c instanceof CustomCard && (((CustomCard)c).isStrike() || ((CustomCard)c).isDefend()) && c.canUpgrade()) {
				c.upgrade();
				AbstractDungeon.player.bottledCardUpgradeCheck(c);
				AbstractDungeon.effectList.add(
					new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), MathUtils.random(0.1F, 0.9F) * Settings.WIDTH,
					MathUtils.random(0.2F, 0.8F) * Settings.HEIGHT));
			}
		}
	}
}
