package basemod;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public abstract class ClickableUIElement {

    protected Texture image;
    protected TextureAtlas.AtlasRegion region;
    protected float x;
    protected float y;
    protected float hb_w;
    protected float hb_h;
    protected Hitbox hitbox;
    private boolean clickable;

    public ClickableUIElement(Texture image) {
        this(image, 0,0, 64.0f, 64.0f);
    }

    public ClickableUIElement(TextureAtlas.AtlasRegion region) {
        this(region, 0, 0, 64.0f, 64.0f);
    }

    public ClickableUIElement(TextureAtlas.AtlasRegion region, float x, float y, float hb_w, float hb_h) {
        this((Texture) null, x, y, hb_w, hb_h);
        this.region = region;
    }

    public ClickableUIElement(Texture image, float x, float y, float hb_w, float hb_h) {
        this.image = image;
        this.x = x * Settings.scale;
        this.y = y * Settings.scale;
        this.hb_w = hb_w * Settings.scale;
        this.hb_h = hb_h * Settings.scale;
        this.hitbox = new Hitbox(this.hb_w, this.hb_h);
        this.hitbox.x = this.x;
        this.hitbox.y = this.y;
        clickable = true;
    }

    public void update(){

        updateHitbox();

        if(this.hitbox.hovered) {
            onHover();
        }
        if(this.hitbox.hovered && InputHelper.justClickedLeft) {
            if(clickable){
                onClick();
            }
        }

    }

    public void setX(float x) {
        this.x = x;
        this.hitbox.x = x;
    }

    public void setY(float y) {
        this.y = y;
        this.hitbox.y = y;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isClickable() {
        return clickable;
    }

    protected void updateHitbox() {
        hitbox.update();
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        if(image != null) {
            sb.draw(image, x, y, image.getWidth() * Settings.scale, image.getHeight() * Settings.scale);
        } else if(region != null) {
            sb.draw(region, x, y, region.packedWidth * Settings.scale, region.packedHeight * Settings.scale);
        }
        renderHitbox(sb);
    }

    protected void renderHitbox(SpriteBatch sb){
        sb.setColor(Color.RED.cpy());
        hitbox.render(sb);
    }

    protected abstract void onHover();
    protected abstract void onClick();

}
