package codemetropolis.toolchain.gui.components.listeners;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import codemetropolis.toolchain.gui.MappingFileEditorDialog;
import codemetropolis.toolchain.gui.QuantizationDialog;
import codemetropolis.toolchain.gui.components.CMTable;
import codemetropolis.toolchain.gui.MappingFileEditorDialog.AssignResult;
import codemetropolis.toolchain.gui.conversions.QuantizationConversion;

/**
 * Listener class for handling changes in the buildable tables.
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 */
public class NewAssigninmentListener implements TableModelListener {
	
	private CMTable changedTable;
	private MappingFileEditorDialog editorDialog;
	
	/**
	 * Constructs a {@link NewAssigninmentListener} instance.
	 */
	public NewAssigninmentListener() {}
	
	/**
	 * Constructs a {@link NewAssigninmentListener} instance with the given parameters.
	 * @param changedTable The table in which the change happened.
	 * @param editorDialog The mapping file editor dialog .
	 */
	public NewAssigninmentListener(CMTable changedTable, MappingFileEditorDialog editorDialog) {
		this.changedTable = changedTable;
		this.editorDialog = editorDialog;
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		String buildableAttribute = (String) changedTable.getValueAt(row , 0);
		String assignedMetric = (String) changedTable.getValueAt(row, 1);
		
		/* The reason why we are checking this condition:
		 * When we are trying to remove a resource, the resource value will be deleted even from the the tables which store the various bindings.
		 * Because of that this event also will be fired. The value of the changed cell will be null in this case --> we will try to call split on null!
		 * So if the cell value changes to null, just don't do anything.  
		 */
		if (assignedMetric != null) {
			String buildableAttributeType = buildableAttribute.split(": ")[1];
			String assignedMetricType = assignedMetric.split(": ")[1];
			AssignResult cell = MappingFileEditorDialog.ASSIGN_RESULT_MATRIX.get(buildableAttributeType)
					.get(assignedMetricType);
			
			if(cell == null) {
				//We are trying to drag a resource... specify its type.
	    		if(assignedMetricType.matches("[0-9]+")) assignedMetricType = "int";
	    		else if(assignedMetricType.matches("[0-9]+.[0-9]+")) assignedMetricType = "float";
	    		else{
	    			assignedMetricType = "string";
	    		}
	    		cell = MappingFileEditorDialog.ASSIGN_RESULT_MATRIX.get(assignedMetricType).get(assignedMetricType);
			}
			
			switch (cell) {
				// We don't have to examine the case when there's no conversion allowed, because it was examined in the checkType method of the TransferHelper class.
				case NO_CONVERSION:
					changedTable.getConversionList().set(row, null);
					//System.out.println("null");
					break;
				case TO_INT:
					Object o = "to_int";
					changedTable.getConversionList().set(row, o);
					//System.out.println(o.toString());
					break;
				case TO_DOUBLE:
					Object o1 = "to_double";
					changedTable.getConversionList().set(row, o1);
					//System.out.println(o1.toString());
					break;
				case NORMALIZE:
					Object o2 = "normalize";
					changedTable.getConversionList().set(row, o2);
					//System.out.println(o2.toString());
					break;
				case QUANTIZATON:
					QuantizationConversion qConv = new QuantizationConversion();
					changedTable.getConversionList().set(row, qConv);
					//System.out.println(qConv.toString());
					new QuantizationDialog(editorDialog, qConv, buildableAttributeType);
					break;
				default:
					break;
			}
		}
	}

}
