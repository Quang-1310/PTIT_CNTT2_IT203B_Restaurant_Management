package presentation;

import model.entity.User;
import util.InputMethod;

import java.util.Scanner;

public class MenuCustomer {
    public void showMenuCustomer(User user){
        int choice;
        do{
            System.out.println("""
                ================== MENU CUSTOMER ==================
                |1. Xem thực đơn                                  |
                |2. Đặt bàn                                       |
                |3. Gọi món                                       |
                |4. Theo dõi trạng thái món ăn                    |
                |5. Thanh toán                                    |
                |6. Thoát                                         |
                ===================================================
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
