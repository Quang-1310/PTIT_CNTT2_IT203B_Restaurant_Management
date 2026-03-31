package presentation;

import business.dao.UserDaoImpl;
import business.service.EmployeeService;
import model.entity.User;
import model.enums.UserRole;
import util.InputMethod;
import util.PasswordHasher;
import validate.Validate;

import java.util.List;

public class ShowManagementEmployee {
    private static EmployeeService employeeService = new EmployeeService();
    private static UserDaoImpl userDao = new UserDaoImpl();
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
                    getAllEmployees();
                    break;
                case 2:
                    addChef();
                    break;
                case 3:
                    updateEmployee();
                    break;
                case 4:
                    deleteEmployee();
                    break;
                case 5:
                    findEmployeeByName();
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

    private static void getAllEmployees() {
        List<User> list = employeeService.getAllEmployee();
        if (list.isEmpty()) {
            System.out.println(Validate.ANSI_YELLOW + "Danh sách nhân viên trống." + Validate.ANSI_RESET);
            return;
        }
        printEmployeeHeader();
        list.forEach(User::displayData);
        printEmployeeFooter();
    }

    private static void addChef() {
        System.out.println("--- THÊM ĐẦU BẾP MỚI ---");
        String name, email, phone, pass;

        while (true) {
            System.out.print("Tên nhân viên: ");
            name = InputMethod.getString();
            if (!Validate.isEmpty(name)) break;
            System.out.println(Validate.ANSI_RED + "Tên không được để trống!" + Validate.ANSI_RESET);
        }

        while (true) {
            System.out.print("Email: ");
            email = InputMethod.getString();
            if (Validate.isValidEmail(email)) break;
            System.out.println(Validate.ANSI_RED + "Email không hợp lệ!" + Validate.ANSI_RESET);
        }


        while (true) {
            System.out.print("Số điện thoại: ");
            phone = InputMethod.getString();
            if (Validate.isValidPhone(phone)) break;
            System.out.println(Validate.ANSI_RED + "SĐT không hợp lệ!" + Validate.ANSI_RESET);
        }

        while (true) {
            System.out.print("Mật khẩu: ");
            pass = InputMethod.getString();
            if (!Validate.isEmpty(pass)) break;
        }

        String hashedPass = PasswordHasher.hashPassword(pass);

        User newChef = new User();
        newChef.setUserName(name);
        newChef.setEmail(email);
        newChef.setPhone(phone);
        newChef.setPassword(hashedPass);
        newChef.setRole(UserRole.CHEF);
        newChef.setActive(true);

        if (userDao.findByEmail(newChef.getEmail()) != null) {
            System.out.println(Validate.ANSI_RED + "Email đã tồn tại" + Validate.ANSI_RESET);
            return;
        }

        if (employeeService.insertEmployee(newChef)) {
            System.out.println(Validate.ANSI_GREEN + "Thêm đầu bếp thành công!" + Validate.ANSI_RESET);
        } else {
            System.out.println(Validate.ANSI_RED + "Thêm thất bại" + Validate.ANSI_RESET);
        }
    }

    private static void updateEmployee() {
        System.out.print("Nhập ID nhân viên cần cập nhật: ");
        int id = InputMethod.getInteger();
        System.out.print("Tên mới: ");
        String name = InputMethod.getString();
        System.out.print("Email mới: ");
        String email = InputMethod.getString();
        System.out.print("SĐT mới: ");
        String phone = InputMethod.getString();
        System.out.print("Mật khẩu mới: ");
        String pass = InputMethod.getString();

        String hashedPass = PasswordHasher.hashPassword(pass);

        if (employeeService.updateEmployee(id, name, email, hashedPass, phone)) {
            System.out.println(Validate.ANSI_GREEN + "Cập nhật thành công!" + Validate.ANSI_RESET);
        } else {
            System.out.println(Validate.ANSI_RED + "Cập nhật thất bại. ID không tồn tại." + Validate.ANSI_RESET);
        }
    }

    private static void deleteEmployee() {
        System.out.print("Nhập ID nhân viên cần xoá: ");
        int id = InputMethod.getInteger();
        do {
            System.out.println("Xác nhận xoá(1 hoặc 2):");
            System.out.print("""
                    1. YES
                    2. NO
                    """);
            System.out.print("Lựa chọn của bạn: ");
            int subChoice = InputMethod.getInteger();
            switch (subChoice){
                case 1:
                    if (employeeService.deleteEmployee(id)) {
                        System.out.println(Validate.ANSI_GREEN + "Xoá thành công!" + Validate.ANSI_RESET);
                    } else {
                        System.out.println(Validate.ANSI_RED + "Xoá thất bại." + Validate.ANSI_RESET);
                    }
                    return;
                case 2:
                    System.out.println(Validate.ANSI_YELLOW + "Đã huỷ" + Validate.ANSI_RESET);
                    return;
                default:
                    System.out.println(Validate.ANSI_RED + "Lựa chọn không hợp lệ" + Validate.ANSI_RESET);
            }
        }while(true);

    }

    private static void findEmployeeByName() {
        System.out.print("Nhập tên nhân viên cần tìm: ");
        String name = InputMethod.getString();
        List<User> list = employeeService.findEmployeeByName(name);
        if (list.isEmpty()) {
            System.out.println(Validate.ANSI_YELLOW + "Không tìm thấy nhân viên nào." + Validate.ANSI_RESET);
        } else {
            printEmployeeHeader();
            list.forEach(User::displayData);
            printEmployeeFooter();
        }
    }

    private static void printEmployeeHeader() {
        System.out.printf("+%s+%s+%s+%s+%s+%s+%s+\n",
                "-".repeat(12), "-".repeat(27), "-".repeat(27), "-".repeat(17), "-".repeat(17), "-".repeat(12), "-".repeat(12));

        System.out.printf("| %-10s | %-25s | %-25s | %-15s | %-15s | %-10s | %-10s |\n",
                "Mã NV", "Tên", "Email", "Password", "Phone", "Role", "Is Active");

        System.out.printf("+%s+%s+%s+%s+%s+%s+%s+\n",
                "-".repeat(12), "-".repeat(27), "-".repeat(27), "-".repeat(17), "-".repeat(17), "-".repeat(12), "-".repeat(12));
    }

    private static void printEmployeeFooter() {
        System.out.printf("+%s+%s+%s+%s+%s+%s+%s+\n",
                "-".repeat(12), "-".repeat(27), "-".repeat(27), "-".repeat(17), "-".repeat(17), "-".repeat(12), "-".repeat(12));
    }
}

