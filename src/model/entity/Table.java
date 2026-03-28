package model.entity;

import model.enums.StatusTable;

public class Table {
    private int tableId;
    private int seatCapacity;
    private StatusTable status;

    public Table() {
    }

    public Table(int tableId, int seatCapacity, StatusTable status) {
        this.tableId = tableId;
        this.seatCapacity = seatCapacity;
        this.status = status;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public StatusTable getStatus() {
        return status;
    }

    public void setStatus(StatusTable status) {
        this.status = status;
    }

    public void displayData(){
        System.out.printf("| %-10s | %-10s | %-10s |\n",
                this.tableId, this.seatCapacity, this.status);
    }
}
