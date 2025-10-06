import java.awt.Graphics;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JPanel;

class MainRect extends JPanel {
    protected final Color bgColor = Color.decode("#0d0a0c");
    private final int chunkGridSize = 1;
    protected final int chunkSize = 32;
    protected final int pixelSizePx = 12;
    protected final Color pixelColor = Color.decode("#aaaaaa");
    private Chunk[][] chunkGrid;
    protected int pixelFlip = 0;

    private final Point pos = new Point(0,0);
    private final Dimension size = new Dimension(chunkGridSize * chunkSize * pixelSizePx, chunkGridSize * chunkSize * pixelSizePx);

    public MainRect() {
        setPreferredSize(this.size);

        this.chunkGrid = new Chunk[chunkGridSize][chunkGridSize];
        for (int y = 0; y < this.chunkGridSize; ++y) {
            for (int x = 0; x < this.chunkGridSize; ++x) {
                this.chunkGrid[y][x] = new Chunk(this, new Point(pixelSizePx * chunkSize * x,pixelSizePx * chunkSize * y), this.chunkSize);
            }
        }
    }

    public void setPixel(Point chunkIndex, Point pixelIndex) {
        this.chunkGrid[chunkIndex.x][chunkIndex.y].state = true;
        this.chunkGrid[chunkIndex.x][chunkIndex.y].pixelGrid[pixelIndex.x][pixelIndex.y][this.pixelFlip].state = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(bgColor);
        g.fillRect(this.pos.x, this.pos.y, this.size.width, this.size.height);

        for (int y = 0; y < this.chunkGridSize; ++y) {
            for (int x = 0; x < this.chunkGridSize; ++x) {
                if (this.chunkGrid[y][x].state) {
                    this.chunkGrid[y][x].draw(g);
                }
            }
        }
    }

    private int getNeighbours(int cx, int cy, int px, int py) {
        int pxFN = (pixelFlip++) % 2;
        return 3;
    }

    public void simulate() {
        this.pixelFlip = (pixelFlip++) % 2;
        for (int cy = 0; cy < this.chunkGridSize; ++cy) {
            for (int cx = 0; cx < this.chunkGridSize; ++cx) {
                if (this.chunkGrid[cy][cx].state) {
                    for (int py = 0; py < this.chunkGridSize; ++py) {
                        for (int px = 0; px < this.chunkGridSize; ++px) {
                            int n = getNeighbours(cx, cy, px, py);
                            if (this.chunkGrid[cy][cx].pixelGrid[py][px][this.pixelFlip].state) {
                                if (n <= 1 || n >= 4) {
                                    this.chunkGrid[cy][cx].pixelGrid[py][px][this.pixelFlip].state = false;
                                }
                            } else {
                                if (n >= 3) {
                                    this.chunkGrid[cy][cx].pixelGrid[py][px][this.pixelFlip].state = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

class Chunk {
    protected MainRect parent;
    protected Point pos;
    protected boolean state = false;

    protected Pixel[][][] pixelGrid;

    public Chunk(MainRect parent, Point pos, int size) {
        this.parent = parent;
        this.pos = pos;

        this.pixelGrid = new Pixel[this.parent.chunkSize][this.parent.chunkSize][2];
        for (int y = 0; y < this.parent.chunkSize; ++y) {
            for (int x = 0; x < this.parent.chunkSize; ++x) {
                this.pixelGrid[y][x][parent.pixelFlip] = new Pixel(this, new Point(this.parent.pixelSizePx * x, this.parent.pixelSizePx * y), this.parent.pixelSizePx - 2, this.parent.pixelColor);
                this.pixelGrid[y][x][(parent.pixelFlip + 1) % 2] = new Pixel(this, new Point(this.parent.pixelSizePx * x, this.parent.pixelSizePx * y), this.parent.pixelSizePx - 2, this.parent.pixelColor);
            }
        }
    }

    protected void draw(Graphics g) {
        for (int y = 0; y < this.parent.chunkSize; ++y) {
            for (int x = 0; x < this.parent.chunkSize; ++x) {
                if (this.pixelGrid[y][x][parent.pixelFlip].state) {
                    this.pixelGrid[y][x][parent.pixelFlip].draw(g);
                }
            }
        }
    }
}

class Pixel extends JPanel {
    private Chunk parent;
    private Point pos;
    private int size;
    private Color color;

    protected boolean state = false;

    public Pixel(Chunk parent, Point pos, int size, Color color) {
        this.parent = parent;
        this.pos = pos;
        this.size = size;
        this.color = color;

        setPreferredSize(new Dimension(this.size, this.size));
    }

    protected void draw(Graphics g) {
        if (this.state) {
            g.setColor(this.color);
        } else {
            g.setColor(this.parent.parent.bgColor);
        }
        g.fillRect(this.parent.pos.x + this.pos.x, this.parent.pos.y + this.pos.y, this.size, this.size);
    }
}
