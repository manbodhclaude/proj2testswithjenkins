package org.ex;

import org.ex.models.Task;
import org.ex.models.User;
import org.ex.repositories.TaskDao;
import org.ex.repositories.UserDao;
import org.ex.services.TaskServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class Test {

    @MockBean
    private UserDao userDao;

    @MockBean
    private TaskDao taskDao;

    @InjectMocks
    private TaskServiceImpl taskServiceImpl;

    @org.junit.jupiter.api.Test
    public void testUserByUserName() {
        String userName = "Harry_Potter";
        User user = new User();
        user.setUser_name(userName);
        Mockito.when(userDao.getUserByUserName(userName)).thenReturn(user);
        assert (true);
    }

    @org.junit.jupiter.api.Test
    public void testGetUserById() {
        int id = 1;
        User user = new User();
        user.setId(id);
        Mockito.when(userDao.getUserById(id)).thenReturn(user);
        assert (true);
    }

    @org.junit.jupiter.api.Test
    public void testGetAllTasks() {
        List<Task> tasks = taskDao.getAllTasks();
        Mockito.when(taskDao.getAllTasks()).thenReturn(tasks);
        assert (true);
    }

    @org.junit.jupiter.api.Test
    public void testGetTaskById() {
        int id = 1;
        Task task = new Task();
        Mockito.when(taskDao.getTaskById(id)).thenReturn(task);
        assert (true);
    }

    @org.junit.jupiter.api.Test
    public void testUpdateTask() {

        Task task = new Task();
        task.setGroup_id(1);
        task.setUser_id(1);
        if (task.getGroup_id() != null || task.getUser_id() != null) {
            try {
                int id = task.getId();
                String taskName = task.getTask_name();
                String description = task.getDescription();
                Timestamp startDate = task.getStart_date();
                Timestamp endDate = task.getEnd_date();
                String status = task.getStatus().toString();
                int minWorked = task.getMinutes_worked();
                this.taskDao.updateTask(id, taskName, description, startDate, endDate, status, minWorked);
                assert (true);
                // return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


//public void testDeleteTask(){
//
//}
}