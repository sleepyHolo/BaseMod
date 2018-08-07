package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import java.util.HashMap;
import java.util.Map;

@SpirePatch(
		cls="com.megacrit.cardcrawl.cards.AbstractCard",
		method=SpirePatch.CLASS
)
public class CardTagsField
{
	public static SpireField<Map<String, Boolean>> tags = new SpireField<>(HashMap::new);
}
