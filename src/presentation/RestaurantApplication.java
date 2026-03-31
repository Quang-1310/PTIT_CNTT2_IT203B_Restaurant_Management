package presentation;

import business.service.UserService;
import model.entity.User;
import model.enums.UserRole;
import util.InputMethod;
import validate.Validate;


public class RestaurantApplication {
    private UserService userService = new UserService();

    public void start() {
        int choice;
        do {
            System.out.println("========= HỆ THỐNG NHÀ HÀNG =========");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký");
            System.out.println("3. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            choice = InputMethod.getInteger();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
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
                    System.out.println(Validate.ANSI_RED + "Lựa chọn không hợp lệ." + Validate.ANSI_RESET);
            }
        } while (choice != 3);
    }

    private void login() {
        String email;
        String pass;
        do{
            System.out.print("Email: ");
            email = InputMethod.getString();
            if(!Validate.isValidEmail(email)){
                System.out.println(Validate.ANSI_RED + "Định dạng email không hợp lệ. Vui lòng nhập lại." + Validate.ANSI_RESET);
            }
            else {
                break;
            }
        }while(true);

        do{
            System.out.print("Password: ");
            pass = InputMethod.getString();
            if(Validate.isEmpty(pass)){
                System.out.println(Validate.ANSI_RED + "Mật khẩu không được để trống. Vui lòng nhập lại." + Validate.ANSI_RESET);
            }
            else {
                break;
            }
        }while(true);


        User user = userService.handleLogin(email, pass);
        if (user != null) {
            System.out.println(Validate.ANSI_GREEN + "Đăng nhập thành công" + Validate.ANSI_RESET);
            redirectByRole(user);
        } else {
            System.out.println(Validate.ANSI_RED + "Sai thông tin tài khoản hoặc tài khoản đã bị khoá!" + Validate.ANSI_RESET);
        }
    }

    private void redirectByRole(User user) {
        switch (user.getRole()) {
            case CUSTOMER:
                new MenuCustomer().showMenuCustomer(user);
                break;
            case CHEF:
                new MenuChef().showMenuChef(user);
                break;
            case MANAGER:
                new MenuManager().showMenuManager(user);
                break;
        }
    }

    private void register() {
        String userName;
        String phone;
        String email;
        String pass;
        String passConfirm;

        do{
            System.out.print("User name: ");
            userName = InputMethod.getString();
            if(Validate.isEmpty(userName)){
                System.out.println(Validate.ANSI_RED + "User name không được để trống. Vui lòng nhập lại." + Validate.ANSI_RESET);
            }
            else {
                break;
            }
        }while(true);

        do{
            System.out.print("Phone: ");
            phone = InputMethod.getString();
            if(!Validate.isValidPhone(phone)){
                System.out.println(Validate.ANSI_RED + "Định dạng SĐT không hợp lệ. Vui lòng nhập lại." + Validate.ANSI_RESET);
            }
            else {
                break;
            }
        }while(true);

        do{
            System.out.print("Email: ");
            email = InputMethod.getString();
            if(!Validate.isValidEmail(email)){
                System.out.println(Validate.ANSI_RED + "Định dạng email không hợp lệ. Vui lòng nhập lại." + Validate.ANSI_RESET);
            }
            else {
                break;
            }
        }while(true);



        do{
            System.out.print("Password: ");
            pass = InputMethod.getString();
            if(Validate.isEmpty(pass)){
                System.out.println(Validate.ANSI_RED + "Mật khẩu không được để trống. Vui lòng nhập lại." + Validate.ANSI_RESET);
            }
            else {
                break;
            }
        }while(true);

        do{
            System.out.print("Password Confirm: ");
            passConfirm = InputMethod.getString();
            if(Validate.isEmpty(passConfirm)){
                System.out.println(Validate.ANSI_RED + "Mật khẩu xác nhận không được để trống. Vui lòng nhập lại." + Validate.ANSI_RESET);
            } else if (!passConfirm.equals(pass)) {
                System.out.println(Validate.ANSI_RED + "Mật khẩu xác nhận không khớp. Vui lòng nhập lại." + Validate.ANSI_RESET);
            } else {
                break;
            }
        }while(true);


        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setEmail(email);
        newUser.setPassword(pass);
        newUser.setPhone(phone);
        newUser.setRole(UserRole.CUSTOMER);

        boolean isRegister = userService.handleRegister(newUser);
        if(isRegister){
            System.out.println(Validate.ANSI_GREEN + "Đăng ký tài khoản thành công" + Validate.ANSI_RESET);
        }else {
            System.out.println(Validate.ANSI_RED + "Đăng ký thất bại. Email đã tồn tại" + Validate.ANSI_RESET);
        }

    }
}
