package codemetropolis.toolchain.gui.utils;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Point;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import codemetropolis.toolchain.gui.MappingFileEditorDialog;
import codemetropolis.toolchain.gui.MappingFileEditorDialog.AssignResult;
import codemetropolis.toolchain.gui.conversions.*;

/**
 * This class is used to handle the transfer of
 * a Transferable to and from Swing components.
 *
 * @author Tamas Keri {@literal <KETWAAT.SZE>}
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 */
public class TransferHelper extends TransferHandler {

	private static final long serialVersionUID = 1L;

    public TransferHelper() {
    }

    /**
     * Checking dropping available
     *
     * @param obj dragged object
     * @param target the target table
     * @param row row of target table
     * @param col column of target table
     * @return boolean available dropping
     */
    public boolean typeChecker (Object obj, JTable target, int row, int col) {
    	int currCol = col -1;

    	if (col == 0) {	return false; }

    	Object value = target.getModel().getValueAt(row, currCol);
    	String dragValue = obj.toString();
    	String dropValue = value.toString();

    	JTable currGardenTable = MappingFileEditorDialog.getGardenTable();
    	JTable currFloorTable = MappingFileEditorDialog.getFloorTable();
    	JTable currCellarTable = MappingFileEditorDialog.getCellarTable();

    	List<Conversion> conversionList = MappingFileEditorDialog.gardenConversion;

    	if (target == currGardenTable) {
    		conversionList = MappingFileEditorDialog.gardenConversion;
    	}

    	if (target == currFloorTable) {
    		conversionList = MappingFileEditorDialog.floorConversion;
    	}

		if (target == currCellarTable) {
			conversionList = MappingFileEditorDialog.cellarConversion;
		}

    	dragValue = dragValue.split(": ")[1];
    	dropValue = dropValue.split(": ")[1];
    	
    	AssignResult cell = MappingFileEditorDialog.ASSIGN_RESULT_MATRIX.get(dropValue).get(dragValue);
    	if(cell == null) {
    		//We are trying to drag a resource... specify its type.
    		if(dragValue.matches("[0-9]+")) dragValue = "int";
    		else if(dragValue.matches("[0-9]+.[0-9]+")) dragValue = "float";
    		else{
    			dragValue = "string";
    		}
    		cell = MappingFileEditorDialog.ASSIGN_RESULT_MATRIX.get(dropValue).get(dragValue);
    	}
    	    	
    	switch (cell) {
    	case CANNOT_ASSIGN:
    		return false;

    	case NO_CONVERSION:
    		conversionList.add(row, new EmptyConversion());
    		return true;

    	case TO_INT:
    		conversionList.add(row, new ToIntConversion());
    		return true;

    	case TO_DOUBLE:
    		conversionList.add(row, new ToDoubleConversion());
    		return true;

    	case NORMALIZE:
    		conversionList.add(row, new NormalizeConversion());
    		return true;

    	case QUANTIZATON:
    		conversionList.add(row, new QuantizationConversion());
    		return true;

    	default:
    		return true;
    	}
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
    }

    @Override
    public boolean canImport(TransferSupport support) {
        // Reject the import by default...
        boolean canImport = false;
        // Can only import into another JTable
        Component comp = support.getComponent();
        if (comp instanceof JTable) {
        	JTable target = (JTable) comp;
        	DropLocation dl = support.getDropLocation();
            Point dp = dl.getDropPoint();
            int dropCol = target.columnAtPoint(dp);
            int dropRow = target.rowAtPoint(dp);

            try {
                // Get the Transferable, we need to check
                // the constraints
                Transferable t = support.getTransferable();
                Object obj = t.getTransferData(DataFlavor.stringFlavor);
                if (obj != null) {
                	canImport = typeChecker(obj, target, dropRow, dropCol);
                }
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }
        }
        return canImport;
    }

    @Override
    public boolean importData(TransferSupport support) {
        // Import failed for some reason...
        boolean imported = false;
        // Only import into JTables...
        Component comp = support.getComponent();
        if (comp instanceof JTable) {
            JTable target = (JTable) comp;
            // Need to know where we are importing to...
            DropLocation dl = support.getDropLocation();
            Point dp = dl.getDropPoint();
            int dropCol = target.columnAtPoint(dp);
            int dropRow = target.rowAtPoint(dp);
            try {
                // Get the Transferable at the heart of it all
                Transferable t = support.getTransferable();
                Object obj = t.getTransferData(DataFlavor.stringFlavor);

                target.setValueAt(obj, dropRow, dropCol);

                imported = true;
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }
        }
        return imported;
    }
}