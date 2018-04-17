package codemetropolis.toolchain.gui.components;

import java.awt.Font;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CMScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	private static final Font SCROLL_PANE_FONT = new Font("Source Sans Pro", Font.PLAIN, 14);

	public CMScrollPane() {
		setFont(SCROLL_PANE_FONT);
	}
	
	public CMScrollPane(JList<String> list, int x, int y, int width, int height) {
		super(list);
		setFont(SCROLL_PANE_FONT);
		setBounds(x, y, width, height);
	}
	
	public CMScrollPane(JTable table, int x, int y, int width, int height) {
		super(table);
		setFont(SCROLL_PANE_FONT);
		setBounds(x, y, width, height);
	}
}
