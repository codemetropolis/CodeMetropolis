package codemetropolis.toolchain.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

/**
 * Custom button class for setting custom defaults for the JButtons we use.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class CMButton extends JButton {

	private static final long serialVersionUID = 1L;

	private static final Font BUTTON_FONT = new Font("Source Sans Pro", Font.PLAIN, 12);


	/**
	 * Constructs a {@link CMButton} instance.
	 *
	 * @param label The label for this button.
	 */
	public CMButton(String label) {
		super(label);
		setFont(BUTTON_FONT);
		
		/* get the element size and set the element size */
		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);

		setContentAreaFilled(false);

	}


	/**
	 * @param g The graphics parameter of object
	 * This method is drawing background color for the button.
	 * The super call represents the normal paintComponent method.
	 * @author Tarkó Máté (h985712)
	 */

	@Override
	protected void paintComponent(Graphics g) {
		if(getModel().isArmed()){
			g.setColor(new Color(255,255,0));
			setForeground(getForeground());
		}else{
			g.setColor(new Color(0,153,153));
			setForeground(Color.BLACK);
			/* If the button is the generate button, we set the background of the button to a distinctive color */
			if(this.getText().equals("Generate")){
				g.setColor(Color.ORANGE);
			}
		}
		g.fillRoundRect(0, 0, getSize().width-1, getSize().height-1, 30, 30);

		super.paintComponent(g);
	}


	/**
	 * @param g The graphics parameter of object
	 * This method is drawing border for rounded button.	 
	 * @author Tarkó Máté (h985712)
	 */
	@Override
	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		g.drawRoundRect(0,0,getSize().width-1,getSize().height-1, 30,30);
	}

	/**
	 * Constructs a {@link CMButton} instance, and sets its position and size.
	 *
	 * @param label  The label for this button.
	 * @param x      The x position on the ui.
	 * @param y      The y position on the ui.
	 * @param width  The width of the element.
	 * @param height The height of the element.
	 */
	public CMButton(String label, int x, int y, int width, int height) {
		this(label);

		setBounds(x, y, width, height);
	}

}

