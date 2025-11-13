package org.example.todoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.todoapp.entity.Todo;
import org.example.todoapp.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    // 전체 조회
    @GetMapping
    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    // 생성
    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        todo.setId(null); // 혹시라도 들어온 id 무시
        todo.setCreatedAt(LocalDateTime.now());
        if (todo.getCompleted() == null) {
            todo.setCompleted(false);
        }
        return todoRepository.save(todo);
    }

    // 수정
    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updated) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (updated.getTitle() != null) {
            todo.setTitle(updated.getTitle());
        }
        if (updated.getCompleted() != null) {
            todo.setCompleted(updated.getCompleted());
        }

        return todoRepository.save(todo);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}
