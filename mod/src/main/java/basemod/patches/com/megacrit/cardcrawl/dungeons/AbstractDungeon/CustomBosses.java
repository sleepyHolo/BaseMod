package basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomBosses
{
	@SpirePatch(
			clz=AbstractDungeon.class,
			method=SpirePatch.CONSTRUCTOR,
			paramtypez={
					String.class,
					String.class,
					AbstractPlayer.class,
					ArrayList.class
			}
	)
	public static class AddBosses
	{
		public static ExprEditor Instrument()
		{
			return new ExprEditor() {
				@Override
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("initializeBoss")) {
						m.replace("{" +
								"$_ = $proceed($$);" +
								AddBosses.class.getName() + ".Do(this);" +
								"}");
					}
				}
			};
		}

		public static void Do(AbstractDungeon dungeon)
		{
			// Don't add custom bosses if player is still on guaranteed bosses due to not seeing all bosses
			if (AbstractDungeon.bossList.size() == 1) {
				return;
			}

			List<String> customBosses = BaseMod.getBossIDs(AbstractDungeon.id);
			// It's important to avoid reshuffling if we have no custom bosses to add. See #229
			if (!customBosses.isEmpty()) {
				AbstractDungeon.bossList.addAll(customBosses);
				Collections.shuffle(AbstractDungeon.bossList, new java.util.Random(AbstractDungeon.monsterRng.randomLong()));
			}
		}
	}

	@SpirePatch(
			clz=AbstractDungeon.class,
			method="setBoss"
	)
	public static class SetBossIcon
	{
		private static final Logger logger = LogManager.getLogger(AbstractDungeon.class.getName());
		public static SpireReturn<Void> Prefix(AbstractDungeon __instance, String key)
		{
			BaseMod.BossInfo bossInfo = BaseMod.getBossInfo(key);
			if (bossInfo != null) {
				// Dispose old map icon
				if (DungeonMap.boss != null) {
					DungeonMap.boss.dispose();
				}
				if (DungeonMap.bossOutline != null) {
					DungeonMap.bossOutline.dispose();
				}

				AbstractDungeon.bossKey = key;
				DungeonMap.boss = bossInfo.loadBossMap();
				DungeonMap.bossOutline = bossInfo.loadBossMapOutline();

				logger.info("[BOSS] " + key);
				return SpireReturn.Return(null);
			}
			return SpireReturn.Continue();
		}
	}

	@SpirePatch(
			clz=ProceedButton.class,
			method="update"
	)
	public static class Ascension20DoubleBoss
	{
		@SpireInsertPatch(
				locator=Locator.class
		)
		public static void Insert(ProceedButton __instance)
		{
			while (AbstractDungeon.bossList.size() > 2) {
				AbstractDungeon.bossList.remove(AbstractDungeon.bossList.size() - 1);
			}
		}

		private static class Locator extends SpireInsertLocator
		{
			@Override
			public int[] Locate(CtBehavior ctBehavior) throws Exception
			{
				Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "ascensionLevel");
				return LineFinder.findInOrder(ctBehavior, finalMatcher);
			}
		}
	}
}
