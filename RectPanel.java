import java.awt.Graphics;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JPanel;

class RectPanel extends JPanel {
    private Point pos;
    private Dimension size;
    private Color color;

    private RectPanel2 panel = new RectPanel2(new Point(200,200), new Dimension(100,100), Color.decode("#ff8888"));

    public RectPanel(Point pos, Dimension size, Color color) {
        this.pos = pos;
        this.size = size;
        this.color = color;

        setPreferredSize(size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(this.color);
        g.fillRect(this.pos.x, this.pos.y, this.size.width, this.size.height);

        g.setColor(this.panel.color);
        g.fillRect(this.panel.pos.x, this.panel.pos.y, this.panel.size.width, this.panel.size.height);
    }
}

class RectPanel2 extends JPanel {
    protected Point pos;
    protected Dimension size;
    protected Color color;

    public RectPanel2(Point pos, Dimension size, Color color) {
        this.pos = pos;
        this.size = size;
        this.color = color;

        setPreferredSize(size);
    }

    @Override
    protected void paintComponent(Graphics g) {}
}
