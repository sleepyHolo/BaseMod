package basemod;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import java.util.function.Consumer;

public class ModLabel {
    private Consumer<ModLabel> update;
    
    public ModPanel parent;
    public String text;
    public float x;
    public float y;
    
    public ModLabel(String labelText, float xPos, float yPos, ModPanel p, Consumer<ModLabel> updateFunc) {
        text = labelText;
        x = xPos*Settings.scale;
        y = yPos*Settings.scale;
        
        parent = p;
        update = updateFunc;
    }
    
    public void render(SpriteBatch sb) {
        FontHelper.renderFontLeftDownAligned(sb, FontHelper.buttonLabelFont, text, x, y, Color.WHITE);
    }
    
    public void update() {
        update.accept(this);
    }
}