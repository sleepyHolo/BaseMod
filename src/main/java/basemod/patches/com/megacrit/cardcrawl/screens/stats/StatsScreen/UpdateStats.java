package basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;

import basemod.BaseMod;

public class UpdateStats {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	public static final float SIZE_PER_CHARACTER = 400.0F;
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.stats.StatsScreen", method="calculateScrollBounds")
	public static class ScrollBounds {
		public static void Postfix(Object __obj_instance) {
			try {
				StatsScreen screen = (StatsScreen) __obj_instance;
				Field scrollUpperBoundField = screen.getClass().getDeclaredField("scrollUpperBound");
				scrollUpperBoundField.setAccessible(true);
				int characterCount = BaseMod.playerStatsMap.keySet().size();
				scrollUpperBoundField.set(screen, scrollUpperBoundField.getFloat(screen) + 
						(SIZE_PER_CHARACTER * characterCount * Settings.scale));
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				logger.error("could not calculate updated scroll bounds");
				logger.error("error was: " + e.toString());
				e.printStackTrace();
			}
		}
	}
	
	@SpirePatch(cls="com.megacrit.cardcrawl.screens.stats.StatsScreen", method="renderStatScreen")
	public static class Render {
		public static void Postfix(Object __obj_instance, Object sbObj) {
			StatsScreen screen = (StatsScreen) __obj_instance;
			SpriteBatch sb = (SpriteBatch) sbObj;
			
			try {
				Field renderYField = screen.getClass().getDeclaredField("renderY");
				renderYField.setAccessible(true);
				
				Method renderHeader = screen.getClass().getDeclaredMethod("renderHeader", sb.getClass(), String.class);
				renderHeader.setAccessible(true);
				
				Method renderCharacterStats = screen.getClass().getDeclaredMethod("renderCharacterStats", sb.getClass(), CharStat.class);
				renderCharacterStats.setAccessible(true);
				
				for (AbstractPlayer.PlayerClass playerClass : BaseMod.playerStatsMap.keySet()) {
					CharStat stats = BaseMod.playerStatsMap.get(playerClass);
					renderYField.set(screen, renderYField.getFloat(screen) - 400.0F * Settings.scale);
					renderHeader.invoke(screen, sb, BaseMod.getTitle(playerClass));
					renderCharacterStats.invoke(screen, sb, stats);
				}
			} catch (NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
				logger.error("could not render stats for custom characters");
				logger.error("error was: " + e.toString());
				e.printStackTrace();
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.stats.StatsScreen", method = "updateFurthestAscent")
	public static class FurthestAscent {
		public static void Postfix(int floor) {
			if (BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass) != null) {
				BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass).furthestAscent(floor);
			}
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.stats.StatsScreen", method = "updateHighestScore")
	public static class HighestScore {
		public static void Postfix(int score) {
			if (BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass) != null) {
				BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass).highestScore(score);
			}
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.stats.StatsScreen", method = "updateHighestDailyScore")
	public static class HighestDailyScore {
		public static void Postfix(int score) {
			if (BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass) != null) {
				BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass).highestDaily(score);
			}
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.stats.StatsScreen", method = "updateVictoryTime")
	public static class VictoryTime {
		public static void Postfix(long time) {
			if (BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass) != null) {
				BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass).updateFastestVictory(time);
			}
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.stats.StatsScreen", method = "incrementFloorClimbed")
	public static class FloorClimbed {
		public static void Postfix() {
			if (BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass) != null) {
				BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass).incrementFloorClimbed();
			}
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.stats.StatsScreen", method = "incrementVictory")
	public static class Victory {
		public static void Postfix(Object cObj) {
			AbstractPlayer.PlayerClass c = (AbstractPlayer.PlayerClass) cObj;
			if (BaseMod.playerStatsMap.get(c) != null) {
				BaseMod.playerStatsMap.get(c).incrementVictory();
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.stats.StatsScreen", method = "incrementDeath")
	public static class Death {
		public static void Postfix(Object cObj) {
			AbstractPlayer.PlayerClass c = (AbstractPlayer.PlayerClass) cObj;
			if (BaseMod.playerStatsMap.get(c) != null) {
				BaseMod.playerStatsMap.get(c).incrementDeath();
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.stats.StatsScreen", method = "incrementEnemySlain")
	public static class EnemySlain {
		public static void Postfix() {
			if (BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass) != null) {
				BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass).incrementEnemySlain();
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.stats.StatsScreen", method = "incrementBossSlain")
	public static class BossSlain {
		public static void Postfix() {
			if (BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass) != null) {
				BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass).incrementBossSlain();
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.stats.StatsScreen", method = "incrementPlayTime")
	public static class PlayTime {
		public static void Postfix(long time) {
			if (BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass) != null) {
				BaseMod.playerStatsMap.get(AbstractDungeon.player.chosenClass).incrementPlayTime(time);
			}
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.stats.StatsScreen", method = "unlockAscension")
	public static class UnlockAscension {
		public static void Postfix(AbstractPlayer.PlayerClass c) {
			if (BaseMod.playerStatsMap.get(c) != null) {
				BaseMod.playerStatsMap.get(c).unlockAscension();
			}
		}
	}

}
