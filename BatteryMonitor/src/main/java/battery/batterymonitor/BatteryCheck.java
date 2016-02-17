package battery.batterymonitor;

import java.util.TimerTask;
import javax.swing.JOptionPane;

/**
 *
 * @author Caitlin
 */
public class BatteryCheck extends TimerTask {

    private final Kernel32.SYSTEM_POWER_STATUS batteryStatus;

    public BatteryCheck(Kernel32.SYSTEM_POWER_STATUS batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    @Override
    public void run() {
        if (!batteryStatus.getBatteryLifePercent().equals("Unknown")) {
            int batteryPercent = new Integer(batteryStatus.getBatteryLifePercent().replace("%", ""));

            if (batteryPercent >= 80) {
                if (batteryStatus.getACLineStatusString().equals("Online")) {
                    JOptionPane.showMessageDialog(null, "Battery Charge: " + batteryPercent + "%. Remove charger.", "BatteryMonitor", JOptionPane.WARNING_MESSAGE);
                }
            } else if (batteryPercent <= 20) {
                if (batteryStatus.getACLineStatusString().equals("Offline")) {
                    JOptionPane.showMessageDialog(null, "Battery Charge: " + batteryPercent + "%. Connect charger.", "BatteryMonitor", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

}
