
package battery.batterymonitor;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import javax.swing.JOptionPane;

/**
 *
 * @author Caitlin
 */
public class Main {
    
    private static Kernel32.SYSTEM_POWER_STATUS batteryStatus;
    
    public static void main(String[] args) {
        batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
        Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
        
        setupTray();
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new BatteryCheck(batteryStatus), 0, 300000);
    }

    public static void setupTray() {
        //Check the SystemTray is supported
        if (SystemTray.isSupported()) {
            try {
                final SystemTray systemTray = SystemTray.getSystemTray();
                
                
                
                PopupMenu popupMenu = new PopupMenu();
                MenuItem statusItem = new MenuItem("Status");
                MenuItem exitItem = new MenuItem("Close");
                popupMenu.add(statusItem);
                popupMenu.add(exitItem);
                
                
                
                Image image = Toolkit.getDefaultToolkit().getImage("src/main/resources/rsz_battery-polarity-icon.png");
                final TrayIcon trayIcon = new TrayIcon(image, "Tray", popupMenu);
                systemTray.add(trayIcon);
                
                ActionListener actionListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        MenuItem menuItem = (MenuItem) actionEvent.getSource();
                        
                        switch (menuItem.getLabel()) {
                            case "Close":
                                systemTray.remove(trayIcon);
                                System.exit(0);
                            case "Status":
                                JOptionPane.showMessageDialog(null, "Battery Life: " + batteryStatus.getBatteryLifePercent());
                                break;
                        }
                    }
                };
                
                statusItem.addActionListener(actionListener);
                exitItem.addActionListener(actionListener);
            } catch (AWTException ex) {
                System.err.println("Error setting up the SystemTray: " + ex);
            }
            
        } else {
            System.err.println("SystemTray is not supported by this system.");
        }

    }

}
