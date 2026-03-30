package business.dao;

import model.entity.ChefTask;

import java.util.List;

public interface IChefDao {
    List<ChefTask> getPendingTasks();

    void updateNextStatus(int orderDetailId);
}
