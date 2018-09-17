package basemod;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class TopPanelGroup {

    private ArrayList<TopPanelItem> topPanelItems;
    private ArrayList<TopPanelItem> activePanelItems;
    private ClickableUIElement leftArrow;
    private ClickableUIElement rightArrow;
    private final int pageItemCount = 3;
    private int currentPage = 1;


    public TopPanelGroup(ArrayList<TopPanelItem> topPanelItems) {
        this.topPanelItems = topPanelItems;
        initArrows();
    }

    public void addPanelItem(TopPanelItem panelItem) {
        this.topPanelItems.add(panelItem);
    }

    public void removePanelItem(TopPanelItem panelItem) {
        this.topPanelItems.remove(panelItem);
    }

    public int size(){
        return this.topPanelItems.size();
    }

    public void render(SpriteBatch sb) {
        int endIndex = currentPage * pageItemCount;
        int startIndex = endIndex - pageItemCount;
        activePanelItems = new ArrayList<>(topPanelItems.subList(startIndex, endIndex > topPanelItems.size() ? topPanelItems.size() : endIndex));
        renderArrows(sb);
        renderItems(sb);
    }


    public void update() {
        int endIndex = (currentPage * pageItemCount);
        int startIndex = endIndex - pageItemCount;
        activePanelItems = new ArrayList<>(topPanelItems.subList(startIndex, endIndex > topPanelItems.size() ? topPanelItems.size() : endIndex));
        updateArrows();
        updateItems();
    }

    private void updateItems(){
        for (int i = 0; i < activePanelItems.size(); i++) {
            TopPanelItem temp = activePanelItems.get(i);
            temp.setX((1175f * Settings.scale) + (i * temp.hb_w));
            temp.setY(1016f * Settings.scale);
            temp.update();
        }
    }

    private void renderItems(SpriteBatch sb) {
        for (int i = 0; i < activePanelItems.size(); i++) {
            TopPanelItem temp = activePanelItems.get(i);
            temp.setX((1175f * Settings.scale) + (i * temp.hb_w));
            temp.setY(1016f * Settings.scale);
            temp.render(sb);

        }
    }

    private void updateArrows(){
        if(currentPage != 1) {
            leftArrow.update();
        }
        if(currentPage < (int)Math.ceil((float)topPanelItems.size() / pageItemCount)) {
            rightArrow.update();
        }
    }

    private void renderArrows(SpriteBatch sb) {
        if(currentPage != 1) {
            leftArrow.render(sb);
        }
        if(currentPage < (int)Math.ceil((float)topPanelItems.size() / pageItemCount)) {
            rightArrow.render(sb);
        }
    }

    private void initArrows(){

        float lX = 1135f;
        float rX = 1360f;
        float y = 1026f;

        leftArrow = new TopPanelArrow(new Texture("img/tinyLeftArrow.png"), lX, y, 32f, 32f) {
            @Override
            protected void onClick() {
                if(currentPage != 1) {
                    currentPage--;
                }
            }
        };

        rightArrow = new TopPanelArrow(new Texture("img/tinyRightArrow.png"), rX, y, 32f, 32f) {
            @Override
            protected void onClick() {
                if(currentPage < (int)Math.ceil((float)topPanelItems.size() / pageItemCount)) {
                    currentPage++;
                }
            }
        };

    }

}
