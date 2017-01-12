package UI;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 * Created by super on 1/11/2017.
 */
public class UploadingTableModel extends DefaultTableModel {
    public UploadingTableModel(){
        super();
        this.addColumn("Order");
        this.addColumn("File name");
        this.addColumn("Movie And Episode Name");
        this.addColumn("Size");
    }
}
