package presentation;

import model.entity.User;
import util.InputMethod;
import validate.Validate;

import java.util.Scanner;

import static presentation.ShowManagementCustomer.showManagementCustomer;
import static presentation.ShowManagementEmployee.showManagementEmployee;
import static presentation.ShowManagementMenu.showManagementMenu;
import static presentation.ShowManagerTable.showManagerTable;

public class MenuManager {
    public void showMenuManager(User user){
        int choice;
        do{
            System.out.println("""
                ================== MENU MANAGER ==================
                |1. Quản lý danh sách bàn ăn                     |
                |2. Quản lý thực đơn (đồ ăn, đồ uống).           |
                |3. Quản lý nhân viên                            |
                |4. Quản lý khách hàng                           |
                |5. Thoát                                        |
                ==================================================
                """);

            System.out.print("Lựa chọn của bạn: ");
            choice = InputMethod.getInteger();
            switch (choice){
                case 1:
                    showManagerTable();
                    break;
                case 2:
                    showManagementMenu();
                    break;
                case 3:
                    showManagementEmployee();
                    break;
                case 4:
                    showManagementCustomer();
                    break;
                case 5:
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
        }while(choice != 5);
    }
}
