package business.service;

import business.dao.OrderImpl;
import business.dao.TableImpl;
import model.entity.Table;
import model.enums.StatusTable;

import java.util.List;

public class TableServiceImpl implements ITableService{
    private TableImpl tableDao = new TableImpl();
    private OrderImpl orderDao = new OrderImpl();
    @Override
    public List<Table> getAllTable() {
        return tableDao.getAllTable();
    }

    @Override
    public boolean addTable(Table newTable) {
        return tableDao.insertTable(newTable);
    }

    @Override
    public boolean updateTable(int id, int newSeatCapacity, StatusTable newStatus) {
        return tableDao.updateTable(id, newSeatCapacity, newStatus);
    }

    @Override
    public boolean deleteTable(int id) {
        return tableDao.deleteTable(id);
    }

    @Override
    public Table findTableById(int id) {
        return tableDao.findTableById(id);
    }

    @Override
    public List<Table> findTableByStatus(StatusTable status) {
        return tableDao.findTableByStatus(status);
    }

    @Override
    public int bookTable(int userId, int tableId) {
        Table table = tableDao.findTableById(tableId);
        if (table == null || table.getStatus() != StatusTable.FREE) {
            return 0;
        }

        int currentOrderid = orderDao.createOrder(userId, tableId);

        if (currentOrderid > 0) {
            tableDao.updateTableStatus(tableId, StatusTable.OCCUPIED);
            return currentOrderid;
        }

        return 0;
    }
}
