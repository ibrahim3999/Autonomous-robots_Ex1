import javax.swing.*;
import java.awt.*;

public class Painter extends JComponent {
	AutoAlgo1 algo;
	public Painter(AutoAlgo1 algo) {
		this.algo = algo;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		algo.paint(g);
	}

}
