package codemetropolis.toolchain.gui.components;

import java.awt.Font;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Custom scroll pane class for setting custom defaults for the JComboBoxes we use.
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE}
 */
public class CMScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	private static final Font SCROLL_PANE_FONT = new Font("Source Sans Pro", Font.PLAIN, 14);
	
	/**
	 * Constructs a {@link CMScrollPane} instance.
	 */
	public CMScrollPane() {
		setFont(SCROLL_PANE_FONT);
	}
	
	/**
	 * Constructs a {@link CMScrollPane} instance and sets its dimensions and position.
	 * @param list {@link JList} which the scroll pane will contain.
	 * @param x The x position on the UI.
	 * @param y The y position on the UI.
	 * @param width The width of the element.
	 * @param height The height of the element.
	 */
	public CMScrollPane(JList<String> list, int x, int y, int width, int height) {
		super(list);
		setFont(SCROLL_PANE_FONT);
		setBounds(x, y, width, height);
	}
	
	/**
	 * Constructs a {@link CMScrollPane} instance and sets its dimensions and position.
	 * @param table {@link JTable} which the scroll pane will contain.
	 * @param x The x position on the UI.
	 * @param y The y position on the UI.
	 * @param width The width of the element.
	 * @param height The height of the element.
	 */
	public CMScrollPane(JTable table, int x, int y, int width, int height) {
		super(table);
		setFont(SCROLL_PANE_FONT);
		setBounds(x, y, width, height);
	}
}
