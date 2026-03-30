package presentation;

import business.service.CustomerService;
import model.entity.User;
import util.InputMethod;
import validate.Validate;

import java.util.List;

public class ShowManagementCustomer {
    private static CustomerService customerService = new CustomerService();
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
                    getAllCustomer();
                    break;
                case 2:
                    findUserByName();
                    break;
                case 3:
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
        }while(choice != 3);
    }

    public static void getAllCustomer(){
        List<User> userList = customerService.getAllCustomer();
        if(userList.isEmpty()) {
            System.out.println(Validate.ANSI_YELLOW + "Danh sách người dùng rỗng" + Validate.ANSI_RESET);
            return;
        }
        printTableCustomerHeader();
        for(User user: userList){
            user.displayData();
        }
        printTableCustomerFooter();

    }

    public static void findUserByName(){
        String name;
        do {
            System.out.print("Nhập tên người dùng cần tìm kiếm: ");
            name = InputMethod.getString();
            if(name.isEmpty()){
                System.out.println(Validate.ANSI_RED + "Tên người dùng không được để trống. Vui lòng nhập lại" + Validate.ANSI_RESET);
                continue;
            }
            break;
        }while(true);

        List<User> userList = customerService.findCustomerByName(name);
        if(userList.isEmpty()){
            System.out.println(Validate.ANSI_YELLOW + "Không tìm thấy người dùng cần tìm" + Validate.ANSI_RESET);
            return;
        }
        printTableCustomerHeader();
        for(User user: userList){
            user.displayData();
        }
        printTableCustomerFooter();

    }

    private static void printTableCustomerHeader() {
        System.out.printf("+%s+%s+%s+%s+%s+%s+%s+\n",
                "-".repeat(12), "-".repeat(27), "-".repeat(27), "-".repeat(17), "-".repeat(17), "-".repeat(12), "-".repeat(12));

        System.out.printf("| %-10s | %-25s | %-25s | %-15s | %-15s | %-10s | %-10s |\n",
                "Mã", "Tên", "Email", "Password", "Phone", "User Role", "Is Active");

        System.out.printf("+%s+%s+%s+%s+%s+%s+%s+\n",
                "-".repeat(12), "-".repeat(27), "-".repeat(27), "-".repeat(17), "-".repeat(17), "-".repeat(12), "-".repeat(12));
    }

    private static void printTableCustomerFooter() {
        System.out.printf("+%s+%s+%s+%s+%s+%s+%s+\n",
                "-".repeat(12), "-".repeat(27), "-".repeat(27), "-".repeat(17), "-".repeat(17), "-".repeat(12), "-".repeat(12));
    }
}
