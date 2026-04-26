package dungeon.crawler.Menu.TestMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener.FocusEvent;

import dungeon.crawler.Menu.BaseLinearMenu;
import dungeon.crawler.Menu.StandardStatusMenu;
import dungeon.crawler.Utils.StringUtils;

public class TestShopSubMenu extends BaseLinearMenu{
    public TestShopSubMenu(Skin skin){
        super(
            skin
        );


        this.addButton(
            "Iron Sword",
            new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("Menu", "this is where the shop would open");
            }},
            "A big iron sword"
        );

        this.addButton("Iron Shield",
            new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.app.log("Menu", "this is where the shop would open");
                }
            },
            "A big wooded shield"
        );
    
        this.pack();
        this.setPosition(50, Gdx.graphics.getHeight() - this.getHeight() - 50);
        this.subStatusMenu = new StandardStatusMenu(skin);
    }

    @Override
    protected void setStage(Stage stage) {


        addFocusListeners();
        super.setStage(stage);
        if(parentMenu != null){
            this.setPosition(this.parentMenu.getOriginX() + 250, Gdx.graphics.getHeight() - this.getHeight() - 50);
        }
        
        if (stage != null) {
            subStatusMenu.setPosition(
                this.getOriginX() + 450, Gdx.graphics.getHeight() - this.getHeight() - 50
            );
            stage.addActor(subStatusMenu);

            refreshAndSetActive(); 
        }
    }
    
    @Override
    public void addFocusListeners(){
        super.addFocusListeners();
        for (Actor actor : this.getChildren()) {
            if(actor instanceof TextButton){
                TextButton button = (TextButton) actor;
                button.addListener(new FocusListener(){
                @Override
                public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                    TextButton button = (TextButton) actor;
                    if (focused) {
                        if(button.getUserObject() instanceof String){
                            subStatusMenu.setText((String)button.getUserObject());
                        } else {
                            subStatusMenu.setText(StringUtils.format("This is the status text for: \n %s", button.getText()));

                        }
                        subStatusMenu.setVisible(true);
                    } else {
                    }
                }
                }); 
            }
        }
    }

}
