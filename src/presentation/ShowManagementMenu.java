package presentation;

import business.service.MenuItemServiceImpl;
import model.entity.Menu_Item;
import model.enums.TypeItem;
import util.InputMethod;
import validate.Validate;

import java.util.List;

public class ShowManagementMenu {
    private static MenuItemServiceImpl menuItemService = new MenuItemServiceImpl();
    public static void showManagementMenu(){
        int choice;
        do{
            System.out.println("""
                ================== MANAGEMENT MENU ========================
                |1. Xem danh sách thực đơn                                |
                |2. Thêm món ăn                                           |
                |3. Cập nhật món ăn                                       |
                |4. Xoá món ăn                                            |
                |5. Tìm kếm món ăn                                        |
                |6. Thoát                                                 |
                ===========================================================
                """);

            System.out.print("Lựa chọn của bạn: ");
            choice = InputMethod.getInteger();
            switch (choice){
                case 1:
                    getAllMenuItem();
                    break;
                case 2:
                    addItem();
                    break;
                case 3:
                    updateItem();
                    break;
                case 4:
                    deleteItem();
                    break;
                case 5:
                    findItemByName();
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


    private static void getAllMenuItem(){
        List<Menu_Item> menuItemList = menuItemService.getAllMenuItem();
        if(menuItemList.isEmpty()){
            System.out.println(Validate.ANSI_RED + "Danh sách menu đang trống" + Validate.ANSI_RESET);
            return;
        }

        printTableMenuHeader();
        for(Menu_Item item: menuItemList){
            item.displayData();
        }
        printTableMenuFooter();

    }

    private static void addItem(){
        List<Menu_Item> menuItemList = menuItemService.getAllMenuItem();
        String itemName;
        int stock = 0;
        TypeItem type = null;
        double price = 0;
        do{
            System.out.print("Mời bạn nhập tên món: ");
            itemName = InputMethod.getString();
            if(itemName.isEmpty()){
                System.out.println(Validate.ANSI_RED + "Tên món không được để trống. Vui lòng nhập lại." + Validate.ANSI_RESET);
            }
            else {
                break;
            }
        }while(true);

        for(Menu_Item item: menuItemList){
            if(item.getItemName().toLowerCase().equals(itemName.toLowerCase())){
                System.out.println(Validate.ANSI_YELLOW + "Món ăn đã tồn tại" + Validate.ANSI_RESET);
                return;
            }
        }

        do {
            System.out.print("Mời bạn nhập số lượng: ");
            stock = InputMethod.getInteger();
            if(stock < 0){
                System.out.println(Validate.ANSI_RED +  "Số lượng món ăn không hợp lệ. Vui lòng nhập lại." + Validate.ANSI_RESET);
            }else {
                break;
            }
        }while(true);

        do {
            System.out.println("Mời bạn chọn loại món:");
            System.out.print("""
                    1. FOOD
                    2. DRINK
                    """);
            System.out.print("Lựa chọn của bạn: ");
            int subChoice = InputMethod.getInteger();
            switch (subChoice){
                case 1:
                     type = TypeItem.FOOD;
                    break;
                case 2:
                    type = TypeItem.DRINK;
                    break;
                case 3:
                    System.out.println(Validate.ANSI_RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại" + Validate.ANSI_RESET);
                    continue;
            }
            break;
        }while(true);

        do {
            System.out.print("Mời bạn nhập giá: ");
            price = InputMethod.getDouble();
            if(price <= 0){
                System.out.println(Validate.ANSI_RED + "Giá món ăn không hợp lệ" + Validate.ANSI_RESET);
            }else {
                break;
            }
        }while(true);

        Menu_Item newItem = new Menu_Item();
        newItem.setItemName(itemName);
        newItem.setStock(stock);
        newItem.setType(type);
        newItem.setPrice(price);

        boolean isInsertItem = menuItemService.insertMenuItem(newItem);
        if(isInsertItem){
            System.out.println(Validate.ANSI_GREEN + "Thêm món thành công." + Validate.ANSI_RESET);
        }else {
            System.out.println(Validate.ANSI_RED + "Thêm món thất bại." + Validate.ANSI_RESET);
        }

    }

    private static void updateItem(){
        System.out.print("Mời bạn nhập id món cần cập nhật: ");
        int id = InputMethod.getInteger();
        Menu_Item item = menuItemService.findItemById(id);
        if(item == null){
            System.out.println(Validate.ANSI_RED + "Không tìm thấy id món cần tìm" + Validate.ANSI_RESET);
            return;
        }

        printTableMenuHeader();
        item.displayData();
        printTableMenuFooter();

        String newItemName;
        int newStock = 0;
        TypeItem newType = null;
        double newPrice = 0;
        do{
            System.out.print("Mời bạn nhập tên món mới: ");
            newItemName = InputMethod.getString();
            if(newItemName.isEmpty()){
                System.out.println(Validate.ANSI_RED + "Tên món không được để trống. Vui lòng nhập lại." + Validate.ANSI_RESET);
            }
            else {
                break;
            }
        }while(true);

        do {
            System.out.print("Mời bạn nhập số lượng mới: ");
            newStock = InputMethod.getInteger();
            if(newStock < 0){
                System.out.println(Validate.ANSI_RED +  "Số lượng món ăn không hợp lệ. Vui lòng nhập lại." + Validate.ANSI_RESET);
            }else {
                break;
            }
        }while(true);

        do {
            System.out.println("Mời bạn chọn loại món mới:");
            System.out.print("""
                    1. FOOD
                    2. DRINK
                    """);
            System.out.print("Lựa chọn của bạn: ");
            int subChoice = InputMethod.getInteger();
            switch (subChoice){
                case 1:
                    newType = TypeItem.FOOD;
                    break;
                case 2:
                    newType = TypeItem.DRINK;
                    break;
                case 3:
                    System.out.println(Validate.ANSI_RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại" + Validate.ANSI_RESET);
                    continue;
            }
            break;
        }while(true);

        do {
            System.out.print("Mời bạn nhập giá mới: ");
            newPrice = InputMethod.getDouble();
            if(newPrice < 0){
                System.out.println(Validate.ANSI_RED + "Giá món ăn không hợp lệ" + Validate.ANSI_RESET);
            }else {
                break;
            }
        }while(true);

        boolean isUpdate = menuItemService.updateMenuItem(id, newItemName, newStock, newType, newPrice);
        if(isUpdate){
            System.out.println(Validate.ANSI_GREEN + "Cập nhật món thành công" + Validate.ANSI_RESET);
        }else {
            System.out.println(Validate.ANSI_RED + "Cập nhật món thất bại" + Validate.ANSI_RESET);
        }
    }

    private static void deleteItem(){
        System.out.print("Mời bạn nhập id món cần xoá: ");
        int id = InputMethod.getInteger();

        Menu_Item menuItem = menuItemService.findItemById(id);
        if(menuItem == null){
            System.out.println(Validate.ANSI_RED + "Không tìm thấy id món cần xoá" + Validate.ANSI_RESET);
            return;
        }

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
                    boolean isDelete = menuItemService.deleteItem(id);
                    if(isDelete){
                        System.out.println(Validate.ANSI_GREEN + "Xoá món thành công" + Validate.ANSI_RESET);
                    }else {
                        System.out.println(Validate.ANSI_RED + "Xoá món thất bại" + Validate.ANSI_RESET);
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

    private static void findItemByName(){
        do {
            System.out.print("Mời bạn nhập tên món cần tìm: ");
            String name = InputMethod.getString();
            if(name.isEmpty()){
                System.out.println("Tên muốn tìm kiếm không được để trống. Vui lòng nhập lại");
            }else {
                List<Menu_Item> items = menuItemService.findItemByName(name);
                if(items.isEmpty()){
                    System.out.println(Validate.ANSI_YELLOW + "Không tìm thấy món" + Validate.ANSI_RESET);
                    break;
                }else {
                    printTableMenuHeader();
                    for(Menu_Item item: items){
                        item.displayData();
                    }
                    printTableMenuFooter();
                    break;
                }
            }
        }while(true);


    }

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
}
