package org.mmeirim.fiap.taskList.infrastructure;

import org.mmeirim.fiap.taskList.domain.Task;
import org.mmeirim.fiap.taskList.domain.TaskRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação do repositório usando JDBC
 * Detalhe de infraestrutura: o domínio não sabe que usa JDBC
 */
@ApplicationScoped
public class TaskRepositoryJDBC implements TaskRepository {

    @Inject
    DatabaseConfig databaseConfig;

    @Override
    public Task save(Task task) {
        String sql = "INSERT INTO tasks (description, status, createdAt) VALUES (?, ?, ?)";

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, task.getDescription());
            stmt.setString(2, task.getStatus());
            stmt.setTimestamp(3, Timestamp.valueOf(task.getCreatedAt()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    Long generatedId = rs.getLong(1);
                    return new Task(
                            generatedId,
                            task.getDescription(),
                            task.getStatus(),
                            task.getCreatedAt()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar tarefa", e);
        }

        return task;
    }

    @Override
    public Optional<Task> findById(Long id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToTask(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tarefa", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Task> findAll() {
        String sql = "SELECT * FROM tasks ORDER BY createdAt";
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = databaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tasks.add(mapResultSetToTask(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tarefas", e);
        }

        return tasks;
    }

    @Override
    public void update(Task task) {
        String sql = "UPDATE tasks SET description = ?, status = ? WHERE id = ?";

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getDescription());
            stmt.setString(2, task.getStatus());
            stmt.setLong(3, task.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tarefa", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar tarefa", e);
        }
    }

    private Task mapResultSetToTask(ResultSet rs) throws SQLException {
        return new Task(
                rs.getLong("id"),
                rs.getString("description"),
                rs.getString("status"),
                rs.getTimestamp("createdAt").toLocalDateTime()
        );
    }
}