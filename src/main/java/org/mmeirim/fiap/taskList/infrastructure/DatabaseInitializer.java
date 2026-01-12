package org.mmeirim.fiap.taskList.infrastructure;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

@ApplicationScoped
public class DatabaseInitializer {

    @Inject
    DatabaseConfig databaseConfig;

    void onStart(@Observes StartupEvent ev) {
        System.out.println("Initializing DB...");
        executarScriptSQL();
        System.out.println("DB initialization successfull");
    }

    private void executarScriptSQL() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("import.sql")) {

            if (is == null) {
                throw new RuntimeException("File import.sql not found");
            }

            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Divide por ponto-e-vírgula para executar múltiplos comandos
            String[] comandos = sql.split(";");

            try (Connection conn = databaseConfig.getConnection();
                 Statement stmt = conn.createStatement()) {

                for (String comando : comandos) {
                    comando = comando.trim();
                    if (!comando.isEmpty() && !comando.startsWith("--")) {
                        stmt.execute(comando);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar script SQL de inicialização", e);
        }
    }
}