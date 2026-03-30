package business.service;

import business.dao.ChefImpl;
import model.entity.ChefTask;

import java.util.List;

public class ChefService implements IChefDao{
    ChefImpl chef =  new ChefImpl();
    @Override
    public List<ChefTask> getPendingTasks() {
        return chef.getPendingTasks();
    }

    @Override
    public void updateNextStatus(int orderDetailId) {
        chef.updateNextStatus(orderDetailId);
    }
}
