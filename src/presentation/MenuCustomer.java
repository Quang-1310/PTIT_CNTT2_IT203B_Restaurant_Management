package presentation;

import business.dao.OrderDetailImpl;
import business.service.MenuItemServiceImpl;
import business.service.OrderService;
import business.service.TableServiceImpl;
import model.entity.*;
import model.enums.StatusOrderDetail;
import model.enums.StatusTable;
import util.InputMethod;
import validate.Validate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static presentation.ShowManagementMenu.*;

public class MenuCustomer {
    private static MenuItemServiceImpl menuItemService = new MenuItemServiceImpl();
    private static TableServiceImpl tableService = new TableServiceImpl();
//    private static OrderDetailImpl orderDetailService = new OrderDetailImpl();
    private static OrderService orderService = new OrderService();
    private int currentOrderId = 0;
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
                    viewMenu();
                    break;
                case 2:
                    bookingTable(user);
                    break;
                case 3:
                    orderItem(user, currentOrderId);
                    break;
                case 4:
                    viewOrderDetailStatus(currentOrderId);
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

    private void viewMenu() {
        List<Menu_Item> list = menuItemService.getAllMenuItem();
        if (list.isEmpty()) {
            System.out.println(Validate.ANSI_RED + "Thực đơn hiện tại chưa có món nào." + Validate.ANSI_RESET);
            return;
        }

        printTableMenuHeader();
        for (Menu_Item item : list) {
            item.displayData();
        }
        printTableMenuFooter();
    }

    private void bookingTable(User user) {
        List<Table> emptyTables = tableService.findTableByStatus(StatusTable.FREE);
        if (emptyTables.isEmpty()) {
            System.out.println(Validate.ANSI_RED + "Rất tiếc, hiện tại không còn bàn trống nào!" + Validate.ANSI_RESET);
            return;
        }

        System.out.println("---------- DANH SÁCH BÀN TRỐNG ----------");
        printTableLineHeader();
        for (Table t : emptyTables) {
            t.displayData();
        }
        printTableLineFooter();

        int tableId;
        do {
            System.out.print("Mời bạn chọn ID bàn muốn đặt: ");
            tableId = InputMethod.getInteger();

            int finalTableId = tableId;
            boolean isValid = emptyTables.stream().anyMatch(t -> t.getTableId() == finalTableId);

            if (!isValid) {
                System.out.println(Validate.ANSI_RED + "ID bàn không hợp lệ hoặc bàn đã có người. Vui lòng chọn lại." + Validate.ANSI_RESET);
            } else {
                break;
            }
        } while (true);

        currentOrderId = tableService.bookTable(user.getUserID(), tableId);
        if (currentOrderId > 0) {
            System.out.println(Validate.ANSI_GREEN + "Đặt bàn thành công! Chào mừng quý khách." + Validate.ANSI_RESET);
        } else {
            System.out.println(Validate.ANSI_RED + "Đặt bàn thất bại. Vui lòng thử lại sau." + Validate.ANSI_RESET);
        }
    }

    private void orderItem(User user, int orderId) {
        if(orderId == 0){
            System.out.println(Validate.ANSI_YELLOW + "Vui lòng đặt bàn trước khi gọi món" + Validate.ANSI_RESET);
            return;
        }
        List<Order_Detail> menuItemList= new ArrayList<>();
        boolean flag = false;
        do {
            System.out.print("Nhập mã món ăn bạn muốn gọi: ");
            int itemId = InputMethod.getInteger();

            Menu_Item item = menuItemService.findItemById(itemId);
            if (item == null) {
                System.out.println(Validate.ANSI_RED + "Món ăn không tồn tại!" + Validate.ANSI_RESET);
                return;
            }

            int quantity;
            do {
                System.out.print("Nhập số lượng muốn mua: ");
                quantity = InputMethod.getInteger();
                int currentStock = menuItemService.getStockById(itemId);
                if (quantity <= 0) {
                    System.out.println(Validate.ANSI_RED + "Số lượng phải lớn hơn 0!" + Validate.ANSI_RESET);
                } else if (quantity > currentStock) {
                    System.out.println(Validate.ANSI_RED + "Rất tiếc, trong kho chỉ còn " + item.getStock() + " sản phẩm. Vui lòng nhập lại." + Validate.ANSI_RESET);
                } else {
                    break;
                }
            } while (true);

            Order_Detail orderDetail = new Order_Detail();
            orderDetail.setOrderId(orderId);
            orderDetail.setItemId(itemId);
            orderDetail.setQuantity(quantity);
            orderDetail.setPriceAtOrder(item.getPrice());
            orderDetail.setStatusItem(StatusOrderDetail.PENDING);
            menuItemList.add(orderDetail);

            System.out.println(Validate.ANSI_GREEN + "Đã thêm " + quantity + " " + item.getItemName() + " vào đơn hàng." + Validate.ANSI_RESET);

            do {
                System.out.print("""
                        1. Tiếp tục đặt món
                        2. Chốt danh sách món
                        """);
                System.out.print("Lựa chọn của bạn: ");
                int subChoice = InputMethod.getInteger();
                switch (subChoice){
                    case 1:
                        break;
                    case 2:
                        flag = true;
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                        continue;
                }
                break;
            }while(true);

            if(flag){
                boolean isSuccess = false;
                try {
                    isSuccess = orderService.placeOrder(orderId, menuItemList);
                } catch (SQLException e) {
                    System.out.println("Lỗi");
                }
                if (isSuccess) {
                    System.out.println(Validate.ANSI_GREEN + "Đặt món thành công! Bếp đang chuẩn bị." + Validate.ANSI_RESET);
                } else {
                    System.out.println(Validate.ANSI_RED + "Đặt món thất bại." + Validate.ANSI_RESET);
                }
                break;
            }
        }while(true);

    }

    private static void viewOrderDetailStatus(int orderId){
        List<OrderDetailStatus> statusOrderDetails = orderService.getTrackingDetails(orderId);
        if(statusOrderDetails.isEmpty()){
            System.out.println(Validate.ANSI_YELLOW + "Danh sách rỗng" + Validate.ANSI_RESET);
        }else {
            printStatusItemHeader();
            for(OrderDetailStatus item: statusOrderDetails){
                item.displayData();
            }
            printStatusItemFooter();
        }
    }

    // Bảng menu
    private static void printTableMenuHeader() {
        System.out.printf("+%s+%s+%s+%s+%s+\n",
                "-".repeat(12), "-".repeat(27), "-".repeat(12), "-".repeat(17), "-".repeat(17));

        System.out.printf("| %-10s | %-25s | %-10s | %-15s | %-15s |\n",
                "Mã Món", "Tên Món", "Số lượng", "Loại", "Giá");

        System.out.printf("+%s+%s+%s+%s+%s+\n",
                "-".repeat(12), "-".repeat(27), "-".repeat(12), "-".repeat(17), "-".repeat(17));
    }

    private static void printTableMenuFooter() {
        System.out.printf("+%s+%s+%s+%s+%s+\n",
                "-".repeat(12), "-".repeat(27), "-".repeat(12), "-".repeat(17), "-".repeat(17));
    }

    // Bảng bàn
    private static void printTableLineHeader() {
        String line = "+" + "-".repeat(12) + "+" + "-".repeat(12) + "+" + "-".repeat(12) + "+";
        System.out.println(line);
        System.out.printf("| %-10s | %-10s | %-10s |\n", "Bàn số", "Sức chứa", "Trạng thái");
        System.out.println(line);
    }

    private static void printTableLineFooter() {
        System.out.println("+" + "-".repeat(12) + "+" + "-".repeat(12) + "+" + "-".repeat(12) + "+");
    }

    // Theo dõi trạng thái món ăn
    private static void printStatusItemHeader() {
        String line = "+" + "-".repeat(27) + "+" + "-".repeat(12) + "+" + "-".repeat(17) + "+";
        System.out.println(line);
        System.out.printf("| %-25s | %-10s | %-15s |\n", "Tên món", "Số lượng", "Trạng thái");
        System.out.println(line);
    }

    private static void printStatusItemFooter() {
        System.out.println("+" + "-".repeat(27) + "+" + "-".repeat(12) + "+" + "-".repeat(17) + "+");
    }
}
