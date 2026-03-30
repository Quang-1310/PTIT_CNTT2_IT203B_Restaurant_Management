package presentation;

import util.InputMethod;
import validate.Validate;

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
}
