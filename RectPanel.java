import java.awt.Graphics;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JPanel;

class MainRect extends JPanel {
    private Point pos;
    private Dimension size;
    private Color color;

    private final int gridSize = 4;
    private final int chunkSize = 8;
    private Chunk[][] grid;

    public MainRect(Point pos, Dimension size, Color color) {
        this.pos = pos;
        this.size = size;
        this.color = color;

        setPreferredSize(size);

        this.grid = new Chunk[gridSize][gridSize];
        for (int y = 0; y < this.gridSize; ++y) {
            for (int x = 0; x < this.gridSize; ++x) {
                this.grid[y][x] = new Chunk(new Point(13 * chunkSize * x,13 * chunkSize * y), this.chunkSize);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(this.color);
        g.fillRect(this.pos.x, this.pos.y, this.size.width, this.size.height);

        for (int y = 0; y < this.gridSize; ++y) {
            for (int x = 0; x < this.gridSize; ++x) {
                this.grid[y][x].paintComponent(g);
            }
        }
    }
}

class Chunk extends JPanel {
    protected Point pos;
    protected int size;

    private Pixel[][] grid;

    public Chunk(Point pos, int size) {
        this.pos = pos;
        this.size = size;
        setPreferredSize(new Dimension(this.size, this.size));

        this.grid = new Pixel[this.size][this.size];
        for (int y = 0; y < this.size; ++y) {
            for (int x = 0; x < this.size; ++x) {
                this.grid[y][x] = new Pixel(new Point(12 * x, 12 * y), 10, Color.decode("#ff8888"));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (int y = 0; y < this.size; ++y) {
            for (int x = 0; x < this.size; ++x) {
                this.grid[y][x].draw(g, this.pos);
            }
        }
    }
}

class Pixel extends JPanel {
    private Point pos;
    private int size;
    private Color color;

    public Pixel(Point pos, int size, Color color) {
        this.pos = pos;
        this.size = size;
        this.color = color;

        setPreferredSize(new Dimension(size, size));
    }

    protected void draw(Graphics g, Point parentPos) {
        g.setColor(this.color);
        g.fillRect(parentPos.x + this.pos.x, parentPos.y + this.pos.y, this.size, this.size);
    }
}
