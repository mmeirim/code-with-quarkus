package org.mmeirim.fiap.taskList.domain;

import java.util.List;
import java.util.Optional;

/**
 * O domínio define O QUE precisa, não COMO será implementado
 */
public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(Long id);

    List<Task> findAll();

    void update(Task task);

    void deleteById(Long id);
}
