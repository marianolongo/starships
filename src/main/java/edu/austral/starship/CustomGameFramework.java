package edu.austral.starship;

import edu.austral.starship.base.framework.GameFramework;
import edu.austral.starship.base.framework.ImageLoader;
import edu.austral.starship.base.framework.WindowSettings;
import processing.core.PGraphics;
import processing.event.KeyEvent;

import java.util.Set;

public class CustomGameFramework implements GameFramework {
    int x = 100;
    int y = 100;
    @Override
    public void setup(WindowSettings windowsSettings, ImageLoader imageLoader) {
        windowsSettings
            .setSize(500, 500);
    }

    @Override
    public void draw(PGraphics graphics, float timeSinceLastDraw, Set<Integer> keySet) {
        graphics.ellipse(x, y, 20, 20);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getKey()){
            case 'w':
                y -= 1;
                break;
            case 's':
                y += 1;
                break;
            case 'a':
                x -= 1;
                break;
            case 'd':
                x += 1;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {

    }
}
