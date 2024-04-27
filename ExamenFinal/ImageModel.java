package examenFinal;

class ImageModel {
    private int[][] pixels;

    public ImageModel(int width, int height) {
        pixels = new int[width][height];
    }

    public void setColor(int x, int y, int color) {
        pixels[x][y] = color;
    }

    public int getColor(int x, int y) {
        return pixels[x][y];
    }

    public void reset() {
        pixels = new int[pixels.length][pixels[0].length];
    }
}
