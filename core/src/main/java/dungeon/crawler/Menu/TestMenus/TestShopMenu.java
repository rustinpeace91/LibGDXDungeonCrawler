package dungeon.crawler.Menu.TestMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

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

        this.buttonList.add(buyButton);
        this.buttonList.add(sellButton);


        this.add(buyButton).row();
        this.add(sellButton).row();

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

    

}
