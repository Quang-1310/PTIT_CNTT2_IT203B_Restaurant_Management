package presentation;

import util.InputMethod;

public class ShowManagementCustomer {
    public static void showManagementCustomer(){
        int choice;
        do{
            System.out.println("""
                ================== MANAGEMENT CUSTOMER ====================
                |1. Xem danh sách khách hàng                              |
                |2. Tìm kiếm khách hàng                                   |
                |3. Thoát                                                 |
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
                default:
                    System.out.println("Lựa chọn không hợp lệ");

            }
        }while(choice != 3);
    }
}
