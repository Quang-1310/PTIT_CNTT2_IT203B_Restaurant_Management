package presentation;

import util.InputMethod;

public class ShowManagementEmployee {
    public static void showManagementEmployee(){
        int choice;
        do{
            System.out.println("""
                ================== MANAGEMENT EMPLOYEE ====================
                |1. Xem danh sách nhân viên                               |
                |2. Thêm nhân viên                                        |
                |3. Cập nhật nhân viên                                    |
                |4. Xoá nhân viên                                         |
                |5. Tìm kiếm nhân viên                                    |
                |6. Thoát                                                 |
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
                case 6:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");

            }
        }while(choice != 6);
    }
}
