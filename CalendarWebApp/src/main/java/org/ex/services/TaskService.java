package org.ex.services;

import org.ex.models.Task;
import org.ex.models.User;
import org.ex.models.dto.UpdateUser;
import org.springframework.context.annotation.Bean;

import java.util.List;

public interface TaskService {

    boolean registerNewUser(User user);

    User getUserByUserName(String username);

    User getUserById(int id);

    boolean updateUser(UpdateUser user);

    List<Task> getAllTasks();

    Task getTaskById(int id);

    boolean persistTask(Task task);

    boolean updateTask(Task task);

    boolean deleteTask(int id);
}
