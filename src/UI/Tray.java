/*
 * Copyright (C) 2016 Laurens Weyn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package UI;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

/**
 *
 * @author Laurens Weyn
 */
public class Tray
{
    private boolean showing = false;
    private UI parent;
    TrayIcon icon;

    public Tray(UI parent)
    {
        this.parent = parent;
        try
        {
            icon = new TrayIcon(ImageIO.read(getClass().getResourceAsStream("/UI/icon.gif")));
            icon.addActionListener(new AbstractAction()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    UI.hidden = false;
                    parent.render();
                    hideTray();
                }
            });
        }catch (IOException ex)
        {
            System.out.println("error loading icon: " + ex);
        }
    }
    
    public void showTray()
    {
        if(showing || SystemTray.isSupported() == false)return;
        SystemTray tray = SystemTray.getSystemTray();
        try
        {
            tray.add(icon);
        }catch (AWTException ex)
        {
            System.out.println("error displaying icon: " + ex);
        } 
        
        
        showing = true;
    }
    public void hideTray()
    {
        if(!showing || SystemTray.isSupported() == false)return;
        SystemTray tray = SystemTray.getSystemTray();
        
        tray.remove(icon);
        
        showing = false;        
    }
}
