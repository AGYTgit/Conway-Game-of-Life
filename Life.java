import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Color;
import java.awt.BorderLayout;

public class Life {
    static int targetMonitorIndex = 1;

    private static void showFrameOnScreen(JFrame frame, int screenIndex) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = ge.getScreenDevices();

        if (screenIndex < screenDevices.length) {
            GraphicsDevice targetDevice = screenDevices[screenIndex];
            Rectangle monitorBounds = targetDevice.getDefaultConfiguration().getBounds();

            int newX = monitorBounds.x + (monitorBounds.width - frame.getWidth()) / 2;
            int newY = monitorBounds.y + (monitorBounds.height - frame.getHeight()) / 2;

            frame.setLocation(newX, newY);
        } else {
            System.err.println("Warning: Monitor index " + screenIndex + " is unavailable. Centering on default screen");
            frame.setLocationRelativeTo(null);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Conway's Game of Life");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());

                MainRect rect = new MainRect();
                rect.setPixel(new Point(0,0), new Point(0,1));
                rect.setPixel(new Point(0,0), new Point(1,2));
                rect.setPixel(new Point(0,0), new Point(2,0));
                rect.setPixel(new Point(0,0), new Point(2,1));
                rect.setPixel(new Point(0,0), new Point(2,2));

                rect.simulate();

                frame.add(rect, BorderLayout.CENTER);

                frame.pack();
                showFrameOnScreen(frame, targetMonitorIndex);
                frame.setVisible(true);
            }
        });
    }
}
