package com.Demo.taskManagement.serviceImpl;

import com.Demo.taskManagement.entity.Task;
import com.Demo.taskManagement.entity.Users;
import com.Demo.taskManagement.exception.APIException;
import com.Demo.taskManagement.exception.TaskNotFound;
import com.Demo.taskManagement.exception.UserNotFound;
import com.Demo.taskManagement.payload.TaskDto;
import com.Demo.taskManagement.repository.TaskRepository;
import com.Demo.taskManagement.repository.UserRepository;
import com.Demo.taskManagement.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public TaskDto saveTask(long userid, TaskDto taskDto) {
        Users user = userRepository.findById(userid).orElseThrow(
                () -> new UserNotFound(String.format("User id %d not found", userid))
        );
        Task task = modelMapper.map(taskDto, Task.class);
        task.setUsers(user);
        //After setting the users, We are storing the data in DB
        Task savedtask =  taskRepository.save(task);
        return modelMapper.map(savedtask, TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllTasks(long userid) {
        userRepository.findById(userid).orElseThrow(
                () -> new UserNotFound(String.format("User id %d not found", userid))
        );
        List<Task> tasks = taskRepository.findAllByUsersId(userid);
        return tasks.stream().map(
                task -> modelMapper.map(task, TaskDto.class)
        ).collect(Collectors.toList());
    }

    @Override
    public TaskDto getTask(long userid, long taskid) {
        Users users = userRepository.findById(userid).orElseThrow(
                () -> new UserNotFound(String.format("User id %d not found", userid))
        );
        Task task = taskRepository.findById(taskid).orElseThrow(
                () -> new TaskNotFound(String.format("Task id %d not found", taskid))
        );
        if(users.getId() != task.getUsers().getId()){
            throw new APIException(String.format("Task id %d not belongs to %d", taskid,userid));
        }
        return modelMapper.map(task,TaskDto.class);
    }

    @Override
    public void deleteTask(long userid, long taskid) {
        Users users = userRepository.findById(userid).orElseThrow(
                () -> new UserNotFound(String.format("User id %d not found", userid))
        );
        Task task = taskRepository.findById(taskid).orElseThrow(
                () -> new TaskNotFound(String.format("Task id %d not found", taskid))
        );
        if(users.getId() != task.getUsers().getId()){
            throw new APIException(String.format("Task id %d not belongs to user id %d", taskid,userid));
        }
        taskRepository.deleteById(taskid); // Delete the task
    }
}
