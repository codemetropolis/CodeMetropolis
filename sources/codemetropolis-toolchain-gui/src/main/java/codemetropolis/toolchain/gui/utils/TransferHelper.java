package codemetropolis.toolchain.gui.utils;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;


public class TransferHelper extends TransferHandler {

	private static final long serialVersionUID = 1L;

    public TransferHelper() {
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
            try {
                // Get the Transferable, we need to check
                // the constraints
                Transferable t = support.getTransferable();
                Object obj = t.getTransferData(DataFlavor.stringFlavor);
                if (obj != null) {
                    canImport = true;
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
            java.awt.Point dp = dl.getDropPoint();
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