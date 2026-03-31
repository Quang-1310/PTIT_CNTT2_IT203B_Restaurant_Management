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
    private static OrderDetailImpl orderDetailService = new OrderDetailImpl();
    private static OrderService orderService = new OrderService();
    private int currentOrderId = 0;
    public void showMenuCustomer(User user){
        this.currentOrderId = orderService.findActiveOrderIdByUserId(user.getUserID());

        int choice;
        do{
            System.out.println("""
                ================== MENU CUSTOMER ==================
                |1. Xem thực đơn                                  |
                |2. Đặt bàn                                       |
                |3. Gọi món                                       |
                |4. Huỷ món                                       |
                |5. Theo dõi trạng thái món ăn                    |
                |6. Thanh toán                                    |
                |7. Thoát                                         |
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
                    int orderIdForOrder = selectActiveOrder(user);
                    if (orderIdForOrder == 0) {
                        System.out.println(Validate.ANSI_YELLOW + "Vui lòng đặt bàn trước khi gọi món!" + Validate.ANSI_RESET);
                    } else {
                        orderItem(user, orderIdForOrder);
                    }
                    break;
                case 4:
                    int orderIdToCancel = getOrderIdFromUser(user, "hủy món");
                    if (orderIdToCancel > 0){
                        cancelItem(orderIdToCancel);
                    }
                    break;
                case 5:
                    int orderIdToTrack = getOrderIdFromUser(user, "theo dõi");
                    if (orderIdToTrack > 0){
                        viewOrderDetailStatus(orderIdToTrack);
                    }
                    break;
                case 6:
                    printFinalBill(user);
                    break;

                case 7:
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
        }while(choice != 7);


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
                }
                else if (currentStock == 0) {
                    System.out.println(Validate.ANSI_YELLOW + "Đã hết hàng" + Validate.ANSI_RESET);
                    return;
                }
                else if (quantity > currentStock) {
                    System.out.println(Validate.ANSI_RED + "Rất tiếc, trong kho chỉ còn " + item.getStock() + " sản phẩm. Vui lòng nhập lại." + Validate.ANSI_RESET);
                }  else {
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

    private static void cancelItem(int orderId){
        List<OrderDetailStatus> details = orderService.getTrackingDetails(orderId);
        if (details.isEmpty()) {
            System.out.println(Validate.ANSI_YELLOW + "Bàn này chưa có món nào." + Validate.ANSI_RESET);
            return;
        }
        int id;
        do {
            System.out.print("Nhập mã món ăn muốn huỷ: ");
            id = InputMethod.getInteger();
            if(id <= 0){
                System.out.println(Validate.ANSI_YELLOW + "Mã món ăn không hợp lệ. Vui lòng nhập lại" + Validate.ANSI_RESET);
            }else {
                break;
            }
        }while(true);

        boolean isCancel = orderService.cancelItem(id);
        if(isCancel){
            System.out.println(Validate.ANSI_GREEN + "Huỷ món thành công" + Validate.ANSI_RESET);
        }else {
            System.out.println(Validate.ANSI_YELLOW + "Huỷ món thất bại. Không tìm thấy món ăn hoặc món ăn đã được chế biến xong" + Validate.ANSI_RESET);
        }
    }

    // lấy orderId để gọi món tương ứng với bàn
    private int selectActiveOrder(User user) {
        List<Order> activeOrders = orderService.findActiveOrdersByUserId(user.getUserID());

        if (activeOrders.isEmpty()) {
            return 0;
        }

        if (activeOrders.size() == 1) {
            return activeOrders.get(0).getOrderID();
        }

        System.out.println(Validate.ANSI_CYAN + "Bạn đang đặt nhiều bàn. Vui lòng chọn bàn muốn thao tác:" + Validate.ANSI_RESET);
        for (int i = 0; i < activeOrders.size(); i++) {
            System.out.printf("%d. Bàn số: %d (Mã đơn: %d)\n",
                    (i + 1), activeOrders.get(i).getTableId(), activeOrders.get(i).getOrderID());
        }

        int choice;
        do {
            System.out.print("Lựa chọn của bạn (1-" + activeOrders.size() + "): ");
            choice = InputMethod.getInteger();
        } while (choice < 1 || choice > activeOrders.size());

        return activeOrders.get(choice - 1).getOrderID();
    }

    // Lấy orderId để huỷ món và xem trạng thái theo từng bàn
    private int getOrderIdFromUser(User user, String actionName) {
        List<Order> activeOrders = orderService.findActiveOrdersByUserId(user.getUserID());

        if (activeOrders.isEmpty()) {
            System.out.println(Validate.ANSI_YELLOW + "Bạn cần đặt bàn trước khi thực hiện " + actionName + "!" + Validate.ANSI_RESET);
            return 0;
        }

        if (activeOrders.size() == 1) {
            return activeOrders.get(0).getOrderID();
        }

        System.out.println("\n--- BẠN ĐANG ĐẶT NHIỀU BÀN ---");
        for (int i = 0; i < activeOrders.size(); i++) {
            System.out.printf("%d. Bàn số: %d (Mã đơn: %d)\n",
                    (i + 1), activeOrders.get(i).getTableId(), activeOrders.get(i).getOrderID());
        }

        int choice;
        do {
            System.out.print("Chọn bàn để " + actionName + " (1-" + activeOrders.size() + "): ");
            choice = InputMethod.getInteger();
        } while (choice < 1 || choice > activeOrders.size());

        return activeOrders.get(choice - 1).getOrderID();
    }

    // Thanh toán
    private void printFinalBill(User user) {
        List<Order> activeOrders = orderService.findActiveOrdersByUserId(user.getUserID());
        if (activeOrders.isEmpty()) {
            System.out.println(Validate.ANSI_YELLOW + "Bạn không có bàn nào đang hoạt động." + Validate.ANSI_RESET);
            return;
        }

        double grandTotal = 0;
        String dLine = "============================================================";
        String sLine = "------------------------------------------------------------";

        System.out.println("\n" + dLine);
        System.out.println("                XÁC NHẬN THANH TOÁN TỔNG                      ");
        System.out.println(dLine);
        System.out.printf(" Khách hàng: %-20s | Ngày: %s\n",
                user.getUserName(),
                java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        for (Order order : activeOrders) {
            double tableTotal = 0;
            System.out.println("\n>>> BÀN SỐ: " + order.getTableId() + " (Đơn: " + order.getOrderID() + ")");
            System.out.println(sLine);
            System.out.printf("| %-22s | %-5s | %-23s |\n", "TÊN MÓN", "SL", "ĐƠN GIÁ");
            System.out.println(sLine);

            List<OrderDetailStatus> items = orderDetailService.getGroupedItemsByOrder(order.getOrderID());

            for (OrderDetailStatus item : items) {
                tableTotal += (item.getQuantity() * item.getPrice());
                System.out.printf("| %-22.22s | %-5d | %-23.2f |\n",
                        item.getItemName(), item.getQuantity(), item.getPrice());
            }

            System.out.println(sLine);
            System.out.printf(" TỔNG TIỀN BÀN %-2d: %22.0f VNĐ\n", order.getTableId(), tableTotal);
            grandTotal += tableTotal;
        }

        System.out.println("\n" + dLine);
        System.out.printf(Validate.ANSI_GREEN + " TỔNG TIỀN TẤT CẢ CÁC BÀN: %26.0f VNĐ\n" + Validate.ANSI_RESET, grandTotal);
        System.out.println(dLine);

        System.out.print("\nXác nhận thanh toán và giải phóng bàn? (1. Có / 2. Quay lại): ");
        int confirm = InputMethod.getInteger();

        if (confirm == 1) {
            boolean success = orderService.checkout(user.getUserID(), activeOrders);
            if (success) {
                System.out.println(Validate.ANSI_GREEN + "Thanh toán thành công! Cảm ơn quý khách." + Validate.ANSI_RESET);
                System.out.println(Validate.ANSI_CYAN + "Bàn của bạn đã được giải phóng." + Validate.ANSI_RESET);
            } else {
                System.out.println(Validate.ANSI_RED + "Có lỗi xảy ra trong quá trình thanh toán. Vui lòng liên hệ nhân viên." + Validate.ANSI_RESET);
            }
        } else {
            System.out.println(Validate.ANSI_YELLOW + "Đã hủy thao tác thanh toán." + Validate.ANSI_RESET);
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
        System.out.printf("+%s+%s+%s+%s+\n", "-".repeat(12), "-".repeat(27), "-".repeat(12), "-".repeat(17));
        System.out.printf("| %-10s | %-25s | %-10s | %-15s |\n","STT", "Tên món", "Số lượng", "Trạng thái");
        System.out.printf("+%s+%s+%s+%s+\n", "-".repeat(12), "-".repeat(27), "-".repeat(12), "-".repeat(17));
    }

    private static void printStatusItemFooter() {
        System.out.printf("+%s+%s+%s+%s+\n", "-".repeat(12), "-".repeat(27), "-".repeat(12), "-".repeat(17));
    }
}
