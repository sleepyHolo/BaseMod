package basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.DungeonMap;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;

public class CustomBosses
{
	@SpirePatch(
			cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon",
			method=SpirePatch.CONSTRUCTOR,
			paramtypes={
					"java.lang.String",
					"java.lang.String",
					"com.megacrit.cardcrawl.characters.AbstractPlayer",
					"java.util.ArrayList"
			}
	)
	public static class AddBosses
	{
		public static ExprEditor Instrument ()
		{
			return new ExprEditor() {
				@Override
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("initializeBoss")) {
						m.replace("{" +
								"$_ = $proceed($$);" +
								"basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.CustomBosses.AddBosses.Nested.Do(this);" +
								"}");
					}
				}
			};
		}

		public static class Nested {
			public static void Do(AbstractDungeon dungeon) {
				// Don't add custom bosses if player is still on guaranteed bosses due to not seeing all bosses
				if (AbstractDungeon.bossList.size() == 1) {
					return;
				}

				AbstractDungeon.bossList.addAll(BaseMod.getBossIDs(AbstractDungeon.id));
				Collections.shuffle(AbstractDungeon.bossList, new java.util.Random(AbstractDungeon.monsterRng.randomLong()));
			}
		}
	}

	@SpirePatch(
			cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon",
			method="setBoss"
	)
	public static class SetBossIcon
	{
		private static final Logger logger = LogManager.getLogger(AbstractDungeon.class.getName());
		public static SpireReturn Prefix(AbstractDungeon __instance, String key)
		{
			BaseMod.BossInfo bossInfo = BaseMod.getBossInfo(key);
			if (bossInfo != null) {
				AbstractDungeon.bossKey = key;
				DungeonMap.boss = bossInfo.bossMap;
				DungeonMap.bossOutline = bossInfo.bossMapOutline;

				logger.info("[BOSS] " + AbstractDungeon.bossList.get(0));
				return SpireReturn.Return(null);
			}
			return SpireReturn.Continue();
		}
	}
}
