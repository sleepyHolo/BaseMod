package basemod;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import java.util.function.Consumer;

public class ModSlider implements IUIElement {
    private static final float BG_X = 1350.0f * Settings.scale;
    private static final float L_X = 1235.0f * Settings.scale;
    private static final float SLIDE_W = 230.0f * Settings.scale;
    
    private Consumer<ModSlider> change;
    private Hitbox hb;
    private Hitbox bgHb;
    private float x;
    private float y;
    private boolean sliderGrabbed = false;
    private String label;
    private String suffix;
    
    public float value = 1.0f;
    public float multiplier;
    public ModPanel parent;
    
    public ModSlider(String lbl, float posX, float posY, float multi, String suf, ModPanel p, Consumer<ModSlider> changeAction) {
        label = lbl;
        suffix = suf;
        
        multiplier = multi;
        parent = p;
        change = changeAction;

        if (posX == -1) {
            posX = L_X;
        } else {
            posX *= Settings.scale;
        }
        x = posX + (SLIDE_W * value);
        y = posY * Settings.scale;
        
        hb = new Hitbox(42.0f * Settings.scale, 38.0f * Settings.scale);
        bgHb = new Hitbox(300.0f * Settings.scale, 38.0f * Settings.scale);
        bgHb.move(BG_X, y);
    }
    
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        
        sb.draw(ImageMaster.OPTION_SLIDER_BG, BG_X - 125.0f, y - 12.0f, 125.0f, 12.0f, 250.0f, 24.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 250, 24, false, false);
        sb.draw(ImageMaster.OPTION_SLIDER, x - 22.0f, y - 22.0f, 22.0f, 22.0f, 44.0f, 44.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 44, 44, false, false);
        
        FontHelper.renderFontCentered(sb, FontHelper.tipBodyFont, label, BG_X - 170.0f * Settings.scale, y, Color.WHITE);
        
        String renderVal = Integer.toString(Math.round(value * multiplier));
        if (sliderGrabbed) {
            FontHelper.renderFontCentered(sb, FontHelper.tipBodyFont, renderVal + suffix, BG_X + 170.0f * Settings.scale, y, Settings.GREEN_TEXT_COLOR);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.tipBodyFont, renderVal + suffix, BG_X + 170.0f * Settings.scale, y, Settings.BLUE_TEXT_COLOR);
        }
        
        this.hb.render(sb);
        this.bgHb.render(sb);
    }
    
    public void update() {        
        hb.update();
        bgHb.update();
        hb.move(x, y);
        
        if (sliderGrabbed) {    
            if (InputHelper.isMouseDown) {
                x = MathHelper.fadeLerpSnap(x, InputHelper.mX);
                x = Math.min(L_X + SLIDE_W, Math.max(x, L_X));
            } else {
                sliderGrabbed = false;
            }
        } else if (InputHelper.justClickedLeft) {
            if (hb.hovered || bgHb.hovered) {
                sliderGrabbed = true;
            }
        }
        
        int oldVal = Math.round(value * multiplier);
        value = (x - L_X) / SLIDE_W;
        
        if (oldVal != Math.round(value * multiplier)) {
            onChange();
        }
    }
    
    public void setValue(float val) {
        x = L_X + (SLIDE_W * val);
    }
    
    private void onChange() {
        change.accept(this);
    }
    
	@Override
	public int renderLayer() {
		return ModPanel.MIDDLE_LAYER;
	}

	@Override
	public int updateOrder() {
		return ModPanel.DEFAULT_UPDATE;
	}
}