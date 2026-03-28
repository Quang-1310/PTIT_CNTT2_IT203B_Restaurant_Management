package business.service;

import business.dao.TableImpl;
import model.entity.Table;
import model.enums.StatusTable;

import java.util.List;

public class TableServiceImpl implements ITableService{
    private TableImpl tableDao = new TableImpl();
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
}
