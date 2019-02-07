package Clases;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.JTable;
import javax.swing.SwingConstants;
import org.pushingpixels.substance.api.renderers.SubstanceDefaultTableCellRenderer;

/**
 *
 * @author Jason
 */
public class Class_editar_tabla {
    
    static public void main(JTable tabla, Object[][] datos){
        Integer cant_cols = tabla.getColumnModel().getColumnCount();
        
        Integer[][] medidas = new Integer[cant_cols][3];
        final Class[] tipos = new Class[cant_cols];
        String[] header = new String[cant_cols];
        final boolean[] editable = new boolean[cant_cols];
        boolean[] resizable = new boolean[cant_cols];
        
        for (int b = 0; b < cant_cols; b++) {
            int min = tabla.getColumnModel().getColumn(b).getMinWidth();
            int pref = tabla.getColumnModel().getColumn(b).getPreferredWidth();
            int max = tabla.getColumnModel().getColumn(b).getMaxWidth();
            
            medidas[b][0] = min;
            medidas[b][1] = pref;
            medidas[b][2] = max;
            
            tipos[b] = tabla.getColumnClass(b);
            header[b] = tabla.getColumnModel().getColumn(b).getHeaderValue().toString();
            editable[b] = tabla.isCellEditable(0, b);
            resizable[b] = tabla.getColumnModel().getColumn(b).getResizable();
        }
        
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            datos,
            header
        ) {
            public Class getColumnClass(int columnIndex) {
                return tipos [columnIndex];
            }
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return editable [columnIndex];
            }
        });
        
        SubstanceDefaultTableCellRenderer tcr = new SubstanceDefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.LEFT);
        
        for (int a = 0; a < cant_cols; a++) {
            //tabla.getColumnModel().getColumn(a).setCellRenderer(new ColorColumnRenderer(Color.lightGray, Color.blue));
            tabla.getColumnModel().getColumn(a).setCellRenderer(tcr);
            
            tabla.getColumnModel().getColumn(a).setMinWidth(medidas[a][0]);
            tabla.getColumnModel().getColumn(a).setPreferredWidth(medidas[a][1]);
            tabla.getColumnModel().getColumn(a).setMaxWidth(medidas[a][2]);
            
            tabla.getColumnModel().getColumn(a).setResizable(resizable[a]);
        }
        
        if (tabla.getTableHeader() != null) {
            tabla.getTableHeader().setReorderingAllowed(false);
        }
    }
}
