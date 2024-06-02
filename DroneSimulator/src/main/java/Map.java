import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Map {
	private boolean[][] map;
	Drone drone;
	Point drone_start_point;

	public Map(String path, Point drone_start_point) {
		this.drone_start_point = drone_start_point;
		try {
			File mapFile = new File(path);
			if (!mapFile.exists()) {
				System.out.println("Map file does not exist: " + path);
				initializeDefaultMap();
				return;
			}
			BufferedImage img_map = ImageIO.read(mapFile);
			if (img_map == null) {
				System.out.println("Map image not found or failed to load: " + path);
				initializeDefaultMap();
			} else {
				this.map = render_map_from_image_to_boolean(img_map);
			}
		} catch (IOException e) {
			e.printStackTrace();
			initializeDefaultMap();
		}
	}

	private void initializeDefaultMap() {
		this.map = new boolean[100][100]; // Default size, adjust as needed
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = true; // Default to non-collidable
			}
		}
	}

	private boolean[][] render_map_from_image_to_boolean(BufferedImage map_img) {
		int w = map_img.getWidth();
		int h = map_img.getHeight();
		boolean[][] map = new boolean[w][h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int clr = map_img.getRGB(x, y);
				int red = (clr & 0x00ff0000) >> 16;
				int green = (clr & 0x0000ff00) >> 8;
				int blue = clr & 0x000000ff;
				map[x][y] = !(red == 0 && green == 0 && blue == 0); // Assuming black is collidable
			}
		}
		return map;
	}

	boolean isCollide(int x, int y) {
		if (x < 0 || x >= map.length || y < 0 || y >= map[0].length) {
			return true; // Consider out of bounds as collidable
		}
		return !map[x][y];
	}

	public void paint(Graphics g) {
		if (this.map == null) {
			System.out.println("Map is null in paint method.");
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (!map[i][j]) {
					g.drawLine(i, j, i, j);
				}
			}
		}
		g.setColor(c);
	}
}
