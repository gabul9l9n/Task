package org.example.controller;

import org.easymock.EasyMockRunner;
import org.example.domain.Task;
import org.example.domain.TaskDto;
import org.example.exception.TaskNotFoundException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;

@RunWith(EasyMockRunner.class)
@FixMethodOrder(MethodSorters.JVM)
public class TaskControllerTest {
    private Task task = new Task("name", "description");
    private TaskDto taskDto = new TaskDto("name", "description");

    private List<Task> taskList = new ArrayList<>();

    {
        taskList.add(task);
    }

    private TaskController taskController;

    @Before
    public void setUp() {
        taskController = createMock(TaskController.class);
    }

    @Test
    public void getAllTasks() {
        expect(taskController.getAllTasks()).andReturn(new ResponseEntity<>(taskList, OK));
        replay(taskController);

        List<Task> listMock = taskController.getAllTasks().getBody();

        assertEquals(listMock, taskList);
    }

    @Test
    public void addTask() {
        expect(taskController.addTask(taskDto)).andReturn(new ResponseEntity<>(task, OK));
        replay(taskController);

        Task taskMock = taskController.addTask(taskDto).getBody();

        assertEquals(taskMock, task);
    }

    @Test
    public void getTask() {
        expect(taskController.getTask(1L)).andReturn(new ResponseEntity<>(task, OK));
        replay(taskController);

        Task taskMock = taskController.getTask(1L).getBody();

        assertEquals(taskMock, task);
    }

    @Test(expected = TaskNotFoundException.class)
    public void getNotFoundTask() {
        taskController.getTask(2L);
        expectLastCall().andThrow(new TaskNotFoundException(2L));
        replay(taskController);

        taskController.getTask(2L);
    }

    @Test
    public void changeTask() {
        expect(taskController.changeTask(1L, taskDto)).andReturn(new ResponseEntity<>(task, OK));
        replay(taskController);

        Task taskMock = taskController.changeTask(1L, taskDto).getBody();

        assertEquals(taskMock, task);
    }

    @Test
    public void deleteTask() {
        taskController.deleteTask(1L);
        expectLastCall().andVoid();

        taskController.deleteTask(1L);
    }

    @Test(expected = TaskNotFoundException.class)
    public void deleteTaskNotFound() {
        taskController.deleteTask(2L);
        expectLastCall().andThrow(new TaskNotFoundException(2L));
        replay(taskController);

        taskController.deleteTask(2L);
    }
}