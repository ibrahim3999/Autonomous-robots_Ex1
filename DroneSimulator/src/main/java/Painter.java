import javax.swing.*;
import java.awt.*;

public class Painter extends JComponent {
	AutoAlgo1 algo1;
	AutoAlgo2 algo2;
	public Painter(AutoAlgo1 algo) {
		this.algo1 = algo;
	}
	public Painter(AutoAlgo2 algo) {
		this.algo2 = algo;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		algo2.paint(g);
	}

}

