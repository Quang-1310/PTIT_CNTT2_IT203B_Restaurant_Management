package business.service;

import model.entity.Table;
import model.enums.StatusTable;

import java.util.List;

public interface ITableService {
    List<Table> getAllTable();
    boolean addTable(Table newTable);
    boolean updateTable(int id, int newSeatCapacity, StatusTable newStatus);
    boolean deleteTable(int id);
    Table findTableById(int id);
    List<Table> findTableByStatus(StatusTable status);

    int bookTable(int userId, int tableId);
}
