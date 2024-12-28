package basemod.patches.com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

@SpirePatch2(
		clz = MiracleEffect.class,
		method = SpirePatch.CONSTRUCTOR,
		paramtypez = {
				Color.class,
				Color.class,
				String.class
		}
)
public class FixLimeColor
{
	@SpireInstrumentPatch
	public static ExprEditor instrument()
	{
		return new ExprEditor() {
			@Override
			public void edit(FieldAccess f) throws CannotCompileException
			{
				if (f.isWriter() && f.getClassName().equals(MiracleEffect.class.getName()) && f.getFieldName().equals("altColor")) {
					f.replace("$proceed($1.cpy());");
				}
			}
		};
	}
}
