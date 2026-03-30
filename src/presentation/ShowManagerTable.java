package presentation;

import business.service.TableServiceImpl;
import model.entity.Table;
import model.enums.StatusTable;
import util.InputMethod;
import validate.Validate;

import java.util.ArrayList;
import java.util.List;

public class ShowManagerTable {
    private static TableServiceImpl tableService = new TableServiceImpl();
    public static void showManagerTable(){
        int choice;
        do{
            System.out.println("""
                ================== MANAGEMENT TABLE ======================
                |1. Xem danh sách các bàn                                 |
                |2. Thêm bàn mới                                          |
                |3. Cập nhật bàn                                          |
                |4. Xoá bàn                                               |
                |5. Tìm kiếm bàn                                          |
                |6. Thoát                                                 |
                ===========================================================
                """);

            System.out.print("Lựa chọn của bạn: ");
            choice = InputMethod.getInteger();
            switch (choice){
                case 1:
                    getAllTable();
                    break;
                case 2:
                    addTable();
                    break;
                case 3:
                    updateTable();
                    break;
                case 4:
                    deleteTable();
                    break;
                case 5:
                    do {
                        System.out.print("""
                                1. Tìm kiếm theo id
                                2. Tìm kiếm theo trạng thái
                                """);
                        System.out.print("Lựa chọn của bạn: ");
                        int subChoice = InputMethod.getInteger();
                        switch (subChoice){
                            case 1:
                                findTableById();
                                break;
                            case 2:
                                findTableByStatus();
                                break;
                            default:
                                System.out.println(Validate.ANSI_RED + "Lựa chọn không hợp lệ." + Validate.ANSI_RESET);
                                continue;
                        }
                        break;
                    }while(true);

                    break;
                case 6:
                    boolean flag = false;
                    do{
                        System.out.println("Xác nhận thoát:");
                        System.out.print("""
                                1. Thoát
                                2. Huỷ
                                """);
                        System.out.print("Lựa chọn của bạn: ");
                        int subChoice = InputMethod.getInteger();
                        switch (subChoice){
                            case 1:
                                flag = true;
                                System.out.println("Tạm biệt! Hẹn gặp lại");
                                break;
                            case 2:
                                choice = 0;
                                break;
                            default:
                                System.out.println(Validate.ANSI_RED + "Lựa chọn không hợp lệ." + Validate.ANSI_RESET);
                        }
                        break;
                    }while(true);

                    if(!flag){
                        continue;
                    }
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }while(choice != 6);
    }

    private static void getAllTable() {
        List<Table> tableList = tableService.getAllTable();
        if (tableList.isEmpty()) {
            System.out.println(Validate.ANSI_RED + "Danh sách bàn đang trống" + Validate.ANSI_RESET);
            return;
        }

        printTableLineHeader();
        for (Table table : tableList) {
            table.displayData();
        }
        printTableLineFooter();
    }

    private static void addTable() {
        int seatCapacity;
        StatusTable status = StatusTable.FREE;

        do {
            System.out.print("Nhập sức chứa (số ghế): ");
            seatCapacity = InputMethod.getInteger();
            if (seatCapacity <= 0) {
                System.out.println(Validate.ANSI_RED + "Sức chứa phải lớn hơn 0. Vui lòng nhập lại." + Validate.ANSI_RESET);
            } else {
                break;
            }
        } while (true);

        Table newTable = new Table();
        newTable.setSeatCapacity(seatCapacity);
        newTable.setStatus(status);

        boolean isInsert = tableService.addTable(newTable);
        if (isInsert) {
            System.out.println(Validate.ANSI_GREEN + "Thêm bàn mới thành công." + Validate.ANSI_RESET);
        } else {
            System.out.println(Validate.ANSI_RED + "Thêm bàn thất bại." + Validate.ANSI_RESET);
        }
    }

    private static void updateTable() {
        System.out.print("Mời bạn nhập ID bàn cần cập nhật: ");
        int id = InputMethod.getInteger();
        Table table = tableService.findTableById(id);
        if (table == null) {
            System.out.println(Validate.ANSI_RED + "Không tìm thấy ID bàn này." + Validate.ANSI_RESET);
            return;
        }

        printTableLineHeader();
        table.displayData();
        printTableLineFooter();

        int newCapacity;
        StatusTable newStatus = null;

        do {
            System.out.print("Nhập sức chứa mới: ");
            newCapacity = InputMethod.getInteger();
            if (newCapacity <= 0) {
                System.out.println(Validate.ANSI_RED + "Sức chứa không hợp lệ." + Validate.ANSI_RESET);
            } else {
                break;
            }
        } while (true);

        do {
            System.out.println("Chọn trạng thái bàn mới:");
            System.out.println("1. FREE (Bàn trống)");
            System.out.println("2. OCCUPIED (Đã đặt)");
            System.out.print("Lựa chọn: ");
            int subChoice = InputMethod.getInteger();
            switch (subChoice){
                case 1:
                    newStatus = StatusTable.FREE;
                    break;
                case 2:
                    newStatus = StatusTable.OCCUPIED ;
                    break;
                default:
                    System.out.println(Validate.ANSI_RED + "Lựa chọn không hợp lệ." + Validate.ANSI_RESET);
                    continue;
            }
            break;
        } while (true);

        boolean isUpdate = tableService.updateTable(id, newCapacity, newStatus);
        if (isUpdate) {
            System.out.println(Validate.ANSI_GREEN + "Cập nhật bàn thành công." + Validate.ANSI_RESET);
        } else {
            System.out.println(Validate.ANSI_RED + "Cập nhật bàn thất bại." + Validate.ANSI_RESET);
        }
    }

    private static void deleteTable() {
        System.out.print("Nhập ID bàn cần xóa: ");
        int id = InputMethod.getInteger();
        Table table = tableService.findTableById(id);
        if (table == null) {
            System.out.println(Validate.ANSI_RED + "Không tìm thấy ID bàn cần xóa." + Validate.ANSI_RESET);
            return;
        }

        do {
            System.out.println("Xác nhận xóa bàn(1 hoặc 2)");
            System.out.print("""
                    1. YES
                    2. NO
                    """);
            System.out.print("Lựa chọn: ");
            int subChoice = InputMethod.getInteger();
            switch (subChoice){
                case 1:
                    if(tableService.deleteTable(id)){
                        System.out.println(Validate.ANSI_GREEN + "Xóa bàn thành công." + Validate.ANSI_RESET);
                    }
                    else {
                        System.out.println(Validate.ANSI_RED + "Xóa bàn thất bại." + Validate.ANSI_RESET);
                    }
                    break;
                case 2:
                    System.out.println(Validate.ANSI_YELLOW + "Đã hủy thao tác." + Validate.ANSI_RESET);
                    return;
                default:
                    System.out.println(Validate.ANSI_RED + "Lựa chọn không hợp lệ." + Validate.ANSI_RESET);
                    continue;
            }
            break;

        } while (true);
    }

    private static void findTableById() {
        System.out.print("Nhập ID bàn cần tìm: ");
        int id = InputMethod.getInteger();
        Table table = tableService.findTableById(id);
        if (table == null) {
            System.out.println(Validate.ANSI_YELLOW + "Không tìm thấy bàn có ID: " + id + Validate.ANSI_RESET);
        } else {
            printTableLineHeader();
            table.displayData();
            printTableLineFooter();
        }
    }

    private static void findTableByStatus() {
        List<Table> tableList;
        do {
            System.out.println("Nhập trạng thái bàn cần tìm (1 hoặc 2): ");
            System.out.print("""
                    1. FREE
                    2. OCCUPIED
                    """);
            System.out.print("Lựa chọn của bạn: ");
            int subChoice = InputMethod.getInteger();
            switch (subChoice){
                case 1:
                    tableList = tableService.findTableByStatus(StatusTable.FREE);
                    printTableLineHeader();
                    for(Table table: tableList){
                        table.displayData();
                    }
                    printTableLineFooter();
                    break;
                case 2:
                    tableList = tableService.findTableByStatus(StatusTable.OCCUPIED);
                    printTableLineHeader();
                    for(Table table: tableList){
                        table.displayData();
                    }
                    printTableLineFooter();
                    break;
                default:
                    System.out.println(Validate.ANSI_RED + "Lựa chọn không hợp lệ." + Validate.ANSI_RESET);
                    continue;
            }
            break;
        }while(true);

        if (tableList.isEmpty()) {
            System.out.println(Validate.ANSI_YELLOW + "Không tìm thấy bàn "+ Validate.ANSI_RESET);
        }
    }

    private static void printTableLineHeader() {
        String line = "+" + "-".repeat(12) + "+" + "-".repeat(12) + "+" + "-".repeat(12) + "+";
        System.out.println(line);
        System.out.printf("| %-10s | %-10s | %-10s |\n", "ID Bàn", "Sức chứa", "Trạng thái");
        System.out.println(line);
    }

    private static void printTableLineFooter() {
        System.out.println("+" + "-".repeat(12) + "+" + "-".repeat(12) + "+" + "-".repeat(12) + "+");
    }
}
