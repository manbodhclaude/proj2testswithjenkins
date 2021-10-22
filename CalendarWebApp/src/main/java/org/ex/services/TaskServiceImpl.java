package org.ex.services;

import org.ex.models.Task;
import org.ex.models.User;
import org.ex.models.dto.UpdateUser;
import org.ex.repositories.TaskDao;
import org.ex.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component("TaskServiceImpl")
public class TaskServiceImpl implements TaskService{

    UserDao userDao;
    TaskDao taskDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public boolean registerNewUser(User user) {
        User queryUser = this.userDao.getUserByUserName(user.getUser_name());

        if(queryUser == null) {
            final String firstName = user.getFirst_name();
            final String lastName = user.getLast_name();
            final String userName = user.getUser_name();
            final String type = user.getUser_type().toString();
            final String email = user.getEmail();
            final String password = user.getUser_password();
            userDao.insertUser(firstName, lastName, userName, type, email, password);
            return true;
        }
        return false;
    }

    @Override
    public User getUserByUserName(String username) {
        return this.userDao.getUserByUserName(username);
    }

    @Override
    public User getUserById(int id) {
        return this.userDao.getUserById(id);
    }

    @Override
    public boolean updateUser(UpdateUser user) {
        int id = user.getId();
        String firstName = user.getFirst_name();
        String lastName = user.getLast_name();
        String userName = user.getUser_name();
        String email = user.getEmail();
        String password = user.getUser_password();

        try {
            if(password != null) {
                this.userDao.updateUserWithPass(id, firstName, lastName, userName, email, password);
            } else {
                this.userDao.updateUserNoPass(id, firstName, lastName, userName, email);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Task> getAllTasks() {
        return this.taskDao.getAllTasks();
    }

    @Override
    public Task getTaskById(int id) {
        return this.taskDao.getTaskById(id);
    }

    @Override
    public boolean persistTask(Task task) {
        if(task.getUser_id() != null) {
            int userId = task.getUser_id();
            User user = this.getUserById(userId);
            if(user != null) {
                return persistUserTask(task);
            }
        } else if(task.getGroup_id() != null) {
            System.out.println("");
            //todo
        }
        return false;
    }

    @Override
    public boolean updateTask(Task task) {
        if(task.getGroup_id() != null || task.getUser_id() != null) {
            try {
                int id = task.getId();
                String taskName = task.getTask_name();
                String description = task.getDescription();
                Timestamp startDate = task.getStart_date();
                Timestamp endDate = task.getEnd_date();
                String status = task.getStatus().toString();
                int minWorked = task.getMinutes_worked();
                this.taskDao.updateTask(id, taskName, description, startDate, endDate, status, minWorked);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean deleteTask(int id) {
        try {
            this.taskDao.deleteTask(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean persistUserTask(Task task) {
        try{
            System.out.println(task);
            Integer userId = task.getUser_id();
            String taskName = task.getTask_name();
            String description = task.getDescription();
            Timestamp startDate = task.getStart_date();
            Timestamp endDate = task.getEnd_date();
            this.taskDao.insertNewUserTask(userId, taskName, description, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
