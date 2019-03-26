package com.kueche.persistenz;

import java.awt.Component;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

public class MyMouseInputAdapter extends MouseInputAdapter{
	int gegenstandPositionX;
	int gegenstandPositionY;
	int startMousePositionX;
	int startMousePositionY;

	@Override
    public void mousePressed(MouseEvent me)
    {
        startMousePositionX = me.getX();
        startMousePositionY = me.getY();
    }

	@Override
    public void mouseDragged(MouseEvent me)
    {
    	//A component is an object having a graphical representation that can be displayed on the screen and 
    	//that can interact with the user. Examples of components are the buttons, checkboxes, and scrollbars 
        Component gegenstand = me.getComponent();
        gegenstandPositionX = gegenstand.getX();
        gegenstandPositionY = gegenstand.getY();
        
        // Determine how much the mouse moved since the initial click
        int x = gegenstandPositionX - startMousePositionX + me.getX();
        int y = gegenstandPositionY - startMousePositionY + me.getY();
        gegenstand.setLocation(x, y);
     }
}

/*
 * 	@Override
    public void mousePressed(MouseEvent me)
    {
        startMousePositionX = me.getX();
        startMousePositionY = me.getY();
    }

	@Override
    public void mouseDragged(MouseEvent me)
    {
    	//A component is an object having a graphical representation that can be displayed on the screen and 
    	//that can interact with the user. Examples of components are the buttons, checkboxes, and scrollbars 
        Component gegenstand = me.getComponent();
        gegenstandPositionX = gegenstand.getX();
        gegenstandPositionY = gegenstand.getY();
        
        // Determine how much the mouse moved since the initial click
        int x = gegenstandPositionX - startMousePositionX + me.getX();
        int y = gegenstandPositionY - startMousePositionY + me.getY();
        gegenstand.setLocation(x, y);
     }
     */
