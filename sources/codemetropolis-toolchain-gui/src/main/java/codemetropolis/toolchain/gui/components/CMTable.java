package codemetropolis.toolchain.gui.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * Custom table class for using in the Mapping file editor dialog.
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 */
public class CMTable extends JTable {
	
	private static final long serialVersionUID = 1L;
	private static final int LIST_MAX_SIZE = 10;

	private List<Object> conversionList;
	private String target;
	private String source;
	
	/**
	 * @see javax.swing.JTable#JTable()
	 */
	public CMTable() {
		super();
		conversionList = new ArrayList<Object>(10);
		setUpConversionList(LIST_MAX_SIZE);
	}
	
	/**
	 * @see javax.swing.JTable#JTable(Object[][], Object[])
	 */
	public CMTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		conversionList = new ArrayList<Object>();
		setUpConversionList(LIST_MAX_SIZE);
	}
	
	/**
	 * @see javax.swing.JTable#JTable(TableModel)
	 */
	public CMTable(TableModel model) {
		super(model);
		conversionList = new ArrayList<Object>();
		setUpConversionList(LIST_MAX_SIZE);
	}
	
	/**
	 * Returns with the {@link List} storing the conversions.
	 * @return The {@link List} storing the conversions.
	 */
	public List<Object> getConversionList() {
		return conversionList;
	}
	
	/**
	 * Fills up the list storing the conversions with dummy data in purpose of avoiding {@link IndexOutOfBoundsException} when setting values.
	 */
	private void setUpConversionList(int size) {
		for(int i = 0; i < size; i++) {
			conversionList.add(null);
		}
	}
	
	/**
	 * Getter for the {@code target} attribute.
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Setter for the {@code target} attribute.
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * Getter for the {@code source} attribute.
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Setter for the {@code source} attribute.
	 */
	public void setSource(String source) {
		this.source = source;
	}
}
