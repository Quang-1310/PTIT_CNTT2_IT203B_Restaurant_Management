package presentation;

import business.service.ChefService;
import model.entity.ChefTask;
import model.entity.User;
import util.InputMethod;
import validate.Validate;

import java.util.List;
import java.util.Scanner;

public class MenuChef {
    private static ChefService chefService = new ChefService();
    public void showMenuChef(User user){
        int choice;
        do{
            System.out.println("""
                ================== MENU CHEF ==================
                |1. Xem danh sách các món khách vừa gọi       |
                |2. Cập nhật tiến độ nấu nướng                |
                |3. Thoát                                     |
                ===============================================
                """);

            System.out.print("Lựa chọn của bạn: ");
            choice = InputMethod.getInteger();
            switch (choice){
                case 1:
                    getPendingTasks();
                    break;
                case 2:
                    updateNextStatus();
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
                    System.out.println("Lựa chọn không hợp lệ");

            }
        }while(choice != 3);
    }

    private static void getPendingTasks(){
        List<ChefTask> chefTaskList = chefService.getPendingTasks();
        if(chefTaskList.isEmpty()){
            System.out.println(Validate.ANSI_YELLOW + "Danh sách rỗng" + Validate.ANSI_RESET);
            return;
        }

        printTableMenuHeader();
        for(ChefTask task: chefTaskList){
            task.displayChefTask();
        }
        printTableMenuFooter();
    }

    private static void updateNextStatus(){
        int id;
        do {
            System.out.print("Nhập id món cập nhật trạng thái: ");
            id = InputMethod.getInteger();
            if(id <= 0){
                System.out.println(Validate.ANSI_YELLOW + "Mã món không hợp lệ" + Validate.ANSI_RESET);
            }else {
                break;
            }
        }while(true);
        chefService.updateNextStatus(id);
    }

    private static void printTableMenuHeader() {
        System.out.printf("+%s+%s+%s+%s+%s+%s+\n",
                "-".repeat(7), "-".repeat(27), "-".repeat(10), "-".repeat(7), "-".repeat(22), "-".repeat(17));

        System.out.printf("| %-5s | %-25s | %-8s | %-5s | %-20s | %-15s |\n",
                "Mã", "Tên Món", "Số lượng", "Bàn", "Thời gian", "Trạng thái");

        System.out.printf("+%s+%s+%s+%s+%s+%s+\n",
                "-".repeat(7), "-".repeat(27), "-".repeat(10), "-".repeat(7), "-".repeat(22), "-".repeat(17));
    }

    private static void printTableMenuFooter() {
        System.out.printf("+%s+%s+%s+%s+%s+%s+\n",
                "-".repeat(7), "-".repeat(27), "-".repeat(10), "-".repeat(7), "-".repeat(22), "-".repeat(17));
    }

}
