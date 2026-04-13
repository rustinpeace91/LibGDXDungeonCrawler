package dungeon.crawler.Menu.TestMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class TestShopMenu extends BaseLinearMenu{
    public TestShopMenu(
        Skin skin
    ){
        super(
            skin,
            false
        );
        TextButton buyButton = new TextButton("Buy", skin);
        TextButton sellButton = new TextButton("Sell", skin);

        this.addButton("Buy", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                BaseLinearMenu nextMenu = new TestShopSubMenu(skin);
                setSubMenu(nextMenu);
                openSubMenu(nextMenu);
            }
        });

        SelectBox<String> selectBox = new SelectBox<>(skin);
        String[] options = {"Level 1", "Level 2", "Level 3", "Settings"};
        selectBox.setItems(options);
        this.add(selectBox).width(150).row();
    
        this.pack();
        this.setPosition(50, Gdx.graphics.getHeight() - this.getHeight() - 50);
        this.addFocusListeners();
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        
        if (stage != null) {
            setMenuVisibility(true); 
        }
    }

    @Override
    public void openSubMenu(BaseLinearMenu nextMenu){
        super.openSubMenu(nextMenu);
        this.setVisible(true);
    }

    

}
