package basemod.patches.com.megacrit.cardcrawl.ui.buttons.CancelButton;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(cls="com.megacrit.cardcrawl.ui.buttons.CancelButton", method="update")
public class ModSettingsCheck {
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getClassName().equals("com.megacrit.cardcrawl.ui.buttons.CancelButton") && m.getMethodName().equals("hide")) {
                    m.replace("{ if (basemod.BaseMod.modSettingsUp) { basemod.BaseMod.modSettingsUp = false; return; } $proceed(); }");
                }
            }
        };
    }
}