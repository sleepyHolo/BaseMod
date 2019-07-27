package basemod.patches.com.megacrit.cardcrawl.cards.DescriptionLine;

import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.regex.Pattern;

@SpirePatch(
		clz=DescriptionLine.class,
		method="tokenizeCN"
)
public class CustomDynamicVariableTokenizeCN
{
	@SpireInsertPatch(
			locator=Locator.class,
			localvars={"tokenized", "i"}
	)
	public static void Insert(String desc, String[] tokenized, int i)
	{
		if (tokenized[i].startsWith("!")) {
			String key = tokenized[i];

			Pattern pattern = Pattern.compile("!(.+)!!");
			java.util.regex.Matcher matcher = pattern.matcher(key);
			if (matcher.find()) {
				key = matcher.group(1);
			}

			DynamicVariable dv = BaseMod.cardDynamicVariableMap.get(key);
			if (dv != null) {
				tokenized[i] = tokenized[i].replace("!", "$");
			}
		}
	}

	private static class Locator extends SpireInsertLocator
	{
		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
		{
			Matcher finalMatcher = new Matcher.MethodCallMatcher(String.class, "replace");
			return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
		}
	}
}
