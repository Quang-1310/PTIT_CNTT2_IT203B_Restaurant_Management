package business.dao;

import model.entity.Menu_Item;
import model.entity.Table;
import model.enums.StatusTable;
import model.enums.TypeItem;

import java.util.List;

public interface ITableDao {
    List<Table> getAllTable();
    boolean insertTable(Table newTable);
    boolean updateTable(int id, int newSeatCapacity, StatusTable newStatus);
    boolean deleteTable(int id);
    Table findTableById(int id);
    List<Table> findTableByStatus(StatusTable status);
}
