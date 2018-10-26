package basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.BaseMod;
import basemod.abstracts.CustomSaveableRaw;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves;

@SpirePatch(clz=CardCrawlGame.class, method="loadPlayerSave")
public class LoadPlayerSaves
{
    public static void Postfix(CardCrawlGame __instance, AbstractPlayer p)
    {
        // Cards
        ModSaves.ArrayListOfJsonElement modCardSaves = ModSaves.modCardSaves.get(CardCrawlGame.saveFile);
        int i = 0;
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card instanceof CustomSaveableRaw) {
                ((CustomSaveableRaw)card).onLoadRaw(modCardSaves == null || i >= modCardSaves.size() ? null : modCardSaves.get(i));
            }
            i++;
        }

        // Relics
        ModSaves.ArrayListOfJsonElement modRelicSaves = ModSaves.modRelicSaves.get(CardCrawlGame.saveFile);
        i = 0;
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic instanceof CustomSaveableRaw) {
                ((CustomSaveableRaw)relic).onLoadRaw(modRelicSaves == null || i >= modRelicSaves.size() ? null : modRelicSaves.get(i));
            }
            i++;
        }

        // Custom save fields
        ModSaves.HashMapOfJsonElement modSaves = ModSaves.modSaves.get(CardCrawlGame.saveFile);
        for (Map.Entry<String,CustomSaveableRaw> field : BaseMod.getSaveFields().entrySet()) {
            field.getValue().onLoadRaw(modSaves == null ? null : modSaves.get(field.getKey()));
        }
    }
}
