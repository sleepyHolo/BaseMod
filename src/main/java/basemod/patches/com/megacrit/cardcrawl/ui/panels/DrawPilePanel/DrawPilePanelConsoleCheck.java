package basemod.patches.com.megacrit.cardcrawl.ui.panels.DrawPilePanel;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(cls="com.megacrit.cardcrawl.ui.panels.DrawPilePanel", method="updatePositions")
public class DrawPilePanelConsoleCheck {
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getClassName().equals("com.badlogic.gdx.Input") && m.getMethodName().equals("isKeyJustPressed")) {
                    m.replace("{ $_ = !basemod.DevConsole.visible && $proceed($$); }");
                }
            }
        };
    }
}
