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
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.WHITE);
        canvasImage = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        graphics = canvasImage.createGraphics();
        clearCanvas();
        currentColor = Color.BLACK;
        setupMenu();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
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
        saveMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar imagen");
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try {
                    FileHandler.saveImage(canvasImage, fileToSave);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al guardar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Cargar imagen");
            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                try {
                    BufferedImage loadedImage = FileHandler.loadImage(fileToLoad);
                    setCanvasImage(loadedImage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
}
