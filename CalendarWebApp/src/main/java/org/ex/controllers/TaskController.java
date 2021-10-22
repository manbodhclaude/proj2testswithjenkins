package org.ex.controllers;

import org.ex.models.Task;
import org.ex.models.User;
import org.ex.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "task")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    TaskService service;

    @Autowired
    public void setTaskService(TaskService taskServiceImpl) {
        this.service = taskServiceImpl;
    }

    @GetMapping("/all")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = this.service.getAllTasks();

        if(tasks != null) {
            if(tasks.size() > 0) {
                return ResponseEntity.ok().body(tasks);
            }
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping(path = "/new")
    @Transactional
    public ResponseEntity createUserTask(@RequestBody Task task) {

        boolean status = this.service.persistTask(task);

        if(status) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(400).build();
    }

    @PutMapping(path = "/update")
    @Transactional
    public ResponseEntity updateTask(@RequestBody Task task) {

        boolean status = this.service.updateTask(task);

        if(status) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(400).build();
    }

    @DeleteMapping(path = "/delete/{id}")
    @Transactional
    public ResponseEntity deleteTask(@PathVariable int id) {

        boolean status = this.service.deleteTask(id);

        //todo: get response from query, instead of this
        Task task = this.service.getTaskById(id); //Shouldn't do this

        if(status && task != null) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(410).build();
    }

    @GetMapping(path="/{id}")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        Task task = this.service.getTaskById(id);
        if(task != null) {
            System.out.println(task);
            return ResponseEntity.ok().body(task);
        }
        return ResponseEntity.status(404).build();
    }

}
