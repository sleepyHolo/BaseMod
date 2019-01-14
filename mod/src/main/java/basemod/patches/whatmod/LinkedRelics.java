package basemod.patches.whatmod;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rewards.RewardItem;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;

@SpirePatch(
		clz=RewardItem.class,
		method="render"
)
public class LinkedRelics
{
	public static ExprEditor Instrument()
	{
		return new ExprEditor() {
			int count = 0;

			@Override
			public void edit(NewExpr e) throws CannotCompileException
			{
				if (count == 0 && e.getClassName().equals(PowerTip.class.getName())) {
					++count;
					e.replace("if (relic.tips.size() > 0) {" +
							"$_ = relic.tips.get(0);" +
							"} else {" +
							"$_ = $proceed($$);" +
							"}");
				}
			}
		};
	}
}
