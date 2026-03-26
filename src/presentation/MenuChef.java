package presentation;

import model.entity.User;
import util.InputMethod;

import java.util.Scanner;

public class MenuChef {
    public void showMenuChef(User user){
        int choice;
        do{
            System.out.println("""
                ================== MENU CHEF ==================
                |1. Xem danh sách các món khác vừa gọi        |
                |2. Cập nhật tiến độ nấu nướng                |
                |3. Thoát                                     |
                ===============================================
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
