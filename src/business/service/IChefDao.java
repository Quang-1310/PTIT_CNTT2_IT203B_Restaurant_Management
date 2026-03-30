package business.service;

import model.entity.ChefTask;

import java.util.List;

public interface IChefDao {
    List<ChefTask> getPendingTasks();

    void updateNextStatus(int orderDetailId);
}
