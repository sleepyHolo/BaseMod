package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.List;

public class DamageHooks {

	@SpirePatch(
			clz=AbstractCard.class,
			method="calculateCardDamage"
	)
	public static class CalculateCardDamage {

		@SpireInsertPatch(
                locator=Locator.class,
				localvars={"tmp"}
		)
		public static void Insert(Object __obj_instance, Object monster, @ByRef float[] tmp) {
			AbstractCard c = (AbstractCard) __obj_instance;
			AbstractMonster mo = (AbstractMonster) monster;
			AbstractPlayer player = AbstractDungeon.player;
			tmp[0] = BaseMod.calculateCardDamage(player, mo, c, tmp[0]);
		}

		private static class Locator extends SpireInsertLocator
		{
			@Override
			public int[] Locate(CtBehavior ctBehavior) throws Exception
			{
				Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
				return LineFinder.findInOrder(ctBehavior, matcher);
			}
		}
	}

	@SpirePatch(
			clz=AbstractCard.class,
			method="calculateCardDamage"
	)
	public static class CalculateCardDamageMulti {

		@SpireInsertPatch(
				locator=Locator.class,
				localvars={"tmp", "i"}
		)
		public static void Insert(Object __obj_instance, Object monster, float[] tmp, int i) {
			AbstractCard c = (AbstractCard) __obj_instance;
			ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
			AbstractPlayer player = AbstractDungeon.player;
			tmp[i] = BaseMod.calculateCardDamage(player, monsters.get(i), c, tmp[i]);
		}

		private static class Locator extends SpireInsertLocator
		{
			@Override
			public int[] Locate(CtBehavior ctBehavior) throws Exception
			{
				Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
				List<Matcher> matchers = new ArrayList<>();
				matchers.add(new Matcher.FieldAccessMatcher(AbstractRoom.class, "monsters"));
				return LineFinder.findInOrder(ctBehavior, matchers, matcher);
			}
		}
	}

	@SpirePatch(
			clz=AbstractCard.class,
			method="applyPowers"
	)
	public static class ApplyPowers {

		@SpireInsertPatch(
                locator=Locator.class,
				localvars={"tmp"}
		)
		public static void Insert(Object __obj_instance, @ByRef float[] tmp) {
			AbstractCard c = (AbstractCard) __obj_instance;
			AbstractPlayer player = AbstractDungeon.player;
			tmp[0] = BaseMod.calculateCardDamage(player, c, tmp[0]);
		}

		private static class Locator extends SpireInsertLocator
		{
			@Override
			public int[] Locate(CtBehavior ctBehavior) throws Exception
			{
				Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
				return LineFinder.findInOrder(ctBehavior, matcher);
			}
		}
	}

	@SpirePatch(
			clz=AbstractCard.class,
			method="applyPowers"
	)
	public static class ApplyPowersMulti {

		@SpireInsertPatch(
                locator=Locator.class,
				localvars={"tmp", "i"}
		)
		public static void Insert(Object __obj_instance, float[] tmp, int i) {
			AbstractCard c = (AbstractCard) __obj_instance;
			ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
			AbstractPlayer player = AbstractDungeon.player;
			tmp[i] = BaseMod.calculateCardDamage(player, monsters.get(i), c, tmp[i]);
		}

		private static class Locator extends SpireInsertLocator
		{
			@Override
			public int[] Locate(CtBehavior ctBehavior) throws Exception
			{
				Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
				List<Matcher> matchers = new ArrayList<>();
				matchers.add(new Matcher.FieldAccessMatcher(AbstractRoom.class, "monsters"));
				return LineFinder.findInOrder(ctBehavior, matchers, matcher);
			}
		}
	}

}
