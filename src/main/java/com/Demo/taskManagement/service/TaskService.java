package com.Demo.taskManagement.service;

import com.Demo.taskManagement.payload.TaskDto;

import java.util.List;

public interface TaskService {
    public TaskDto saveTask(long userid, TaskDto taskDto);
    public List<TaskDto> getAllTasks(long userid);
    public TaskDto getTask(long userid, long taskid);
    public void deleteTask(long userid, long taskid);
}
