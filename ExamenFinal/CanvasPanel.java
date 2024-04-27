package examenFinal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

class CanvasPanel extends JPanel implements Serializable {
    private static final long serialVersionUID = 1L;
    private BufferedImage canvasImage;
    private Graphics2D graphics;
    private Color currentColor;
    private JMenuItem saveMenuItem;
    private JMenuItem loadMenuItem;
    private JMenuBar menuBarContainer;
    private int cellSize = 10;

    public CanvasPanel() {
        setPreferredSize(new Dimension(1800, 1000));
        setBackground(Color.WHITE);
        canvasImage = new BufferedImage(1800, 1000, BufferedImage.TYPE_INT_ARGB);
        graphics = canvasImage.createGraphics();
        clearCanvas();
        currentColor = Color.BLACK;
        setupMenu();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point mousePoint = e.getPoint();
                Point gridPosition = Utilities.calculateGridPosition(mousePoint, cellSize);
                int x = gridPosition.x;
                int y = gridPosition.y;
                if (SwingUtilities.isLeftMouseButton(e)) {
                    graphics.setColor(currentColor);
                    graphics.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
                repaint();
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX() / cellSize;
                int y = e.getY() / cellSize;
                if (SwingUtilities.isLeftMouseButton(e)) {
                    graphics.setColor(currentColor);
                    graphics.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
                repaint();
            }
        });
    }

    private void setupMenu() {
        menuBarContainer = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        saveMenuItem = new JMenuItem("Guardar");
        loadMenuItem = new JMenuItem("Cargar");
        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        menuBarContainer.add(fileMenu);
    }

    public JMenuItem getSaveMenuItem() {
        return saveMenuItem;
    }

    public JMenuItem getLoadMenuItem() {
        return loadMenuItem;
    }

    public BufferedImage getCanvasImage() {
        return canvasImage;
    }

    public void setCanvasImage(BufferedImage canvasImage) {
        this.canvasImage = canvasImage;
        this.graphics = canvasImage.createGraphics();
        repaint();
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvasImage, 0, 0, null);
        drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.GRAY);
        for (int x = 0; x < getWidth(); x += cellSize) {
            g.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += cellSize) {
            g.drawLine(0, y, getWidth(), y);
        }
    }

    public void clearCanvas() {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        repaint();
    }

    public void updateImageSize() {
        BufferedImage newCanvasImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D newGraphics = newCanvasImage.createGraphics();
        newGraphics.drawImage(canvasImage, 0, 0, null);
        this.canvasImage = newCanvasImage;
        this.graphics = newGraphics;
        repaint();
    }

    public JMenuBar getMenuBarContainer() {
        return menuBarContainer;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
        repaint();
    }
}
