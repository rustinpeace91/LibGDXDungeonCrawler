package dungeon.crawler.Menu.InputHandlers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import dungeon.crawler.Menu.BaseLinearMenu;
import dungeon.crawler.Observers.MenuInputObserver;

public class MenuInputHandler extends InputAdapter{
public Stage uiStage;
public BaseLinearMenu currentMenuTable;
public BaseLinearMenu rootMenu;
public boolean showMenu = false;

public int menuColumns = 0;
public int menuRows = 0;
public int menuColumnIndex = 0;
public int menuRowIndex = 0;
public TextButton currentButton;

// if another input handler is used but the current menu is visible
private boolean handlerDisabled;

private final List<MenuInputObserver> listeners = new ArrayList<>();

    public MenuInputHandler(
        Stage uiStage,
        BaseLinearMenu currentMenuTable
    ) {
        this.uiStage = uiStage;
        this.currentMenuTable = currentMenuTable;
        this.rootMenu = currentMenuTable;
        
        handlerDisabled = false;
    }


    private void updateCurrentMenuFromFocus() {
        Actor focused = uiStage.getKeyboardFocus();
        if (focused != null) {
            Actor parent = focused;
            while (parent != null) {
                if (parent instanceof BaseLinearMenu) {
                    this.currentMenuTable = (BaseLinearMenu) parent;
                    return;
                }
                parent = parent.getParent();
            }
        } else {
            this.currentMenuTable = rootMenu;
            return;
        }
    }

    @Override
    public boolean keyDown(int keyCode) {
        if(handlerDisabled) {
            return true;
        }
        updateCurrentMenuFromFocus();
        if(keyCode == Input.Keys.E && rootMenu.isToggleable) {
            boolean rootVisible = rootMenu.isVisible();
            
            if(rootVisible) {
                currentMenuTable.closeMenuStack(); 
            } else {
                rootMenu.refreshAndSetActive();
            }
            
            notifyOnMenuToggled(!rootVisible);
            return true;
        }

        if(menuFocusAvailable()) {
            if(keyCode == Input.Keys.ENTER){
                Actor focused = uiStage.getKeyboardFocus();
                if (focused instanceof Button) {
                    Gdx.app.log("Button", "BTTTN clicked");
                    ((Button) focused).toggle(); // Toggles isChecked and fires the listener
                }
            }
            if(keyCode == Input.Keys.DOWN) {
                currentMenuTable.advanceMenuSelection(1);
            }
            if(keyCode == Input.Keys.UP) {
                currentMenuTable.advanceMenuSelection(-1);
            }
            if(keyCode == Input.Keys.BACKSPACE){
                currentMenuTable.returnToParentMenu();
            }
        }
        return false;
    }
    public void setCurrentMenuTable(BaseLinearMenu currentMenuTable) {
        this.currentMenuTable = currentMenuTable;
    }

    public boolean menuFocusAvailable(){
        if(
            currentMenuTable != null &&
            currentMenuTable.isVisible()
        ){
            return true;
        }
        return false;
    }

    public void notifyOnMenuToggled(boolean showMenu) {
        for (MenuInputObserver listener : listeners) {
            listener.onMenuToggled(showMenu);
        }
    }

    public void addListener(MenuInputObserver listener) {
        if(listener != null) {
            listeners.add(listener);
        }
    }

    public void removeListener(MenuInputObserver listener) {
        if(listener != null) {
            listeners.remove(listener);
        }
    }

    public void setHandlerDisabled(boolean value){
        handlerDisabled = value;
    }


}
