package presentation;

import model.entity.User;
import util.InputMethod;

import java.util.Scanner;

public class MenuManager {
    public void showMenuManager(User user){
        int choice;
        do{
            System.out.println("""
                ================== MENU MANAGER ==================
                |1. Quản lý danh sách bàn ăn                     |
                |2. Quản lý thực đơn (đồ ăn, đồ uống).           |
                |3. Quản lý nhân viên                            |
                |4. Thoát                                        |
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
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");

            }
        }while(choice != 6);
    }

    public static void showManagerTable(){
        int choice;
        do{
            System.out.println("""
                ================== MENU MANAGEMENT TABLE ==================
                |1. Xem danh sách các bàn                                 |
                |2. Thêm bàn mới                                          |
                |3. Cập nhật bàn                                          |
                |4. Xoá bàn                                               |
                |5. Tìm kiếm bàn                                          |
                ===========================================================
                """);

            System.out.print("Lựa chọn của bạn: ");
            choice = InputMethod.getInteger();
            switch (choice){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");

            }
        }while(choice != 5);
    }

    public static void showManagementMenu(){
        int choice;
        do{
            System.out.println("""
                ================== MENU MANAGEMENT TABLE ==================
                |1. Xem danh sách thực đơn                                |
                |2. Thêm món ăn                                           |
                |3. Cập món ăn                                            |
                |4. Xoá món ăn                                            |
                |5. Tìm món ăn                                            |
                ===========================================================
                """);

            System.out.print("Lựa chọn của bạn: ");
            choice = InputMethod.getInteger();
            switch (choice){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");

            }
        }while(choice != 5);
    }
}
