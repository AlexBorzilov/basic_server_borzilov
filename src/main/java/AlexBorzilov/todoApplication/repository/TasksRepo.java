package AlexBorzilov.todoApplication.repository;

import AlexBorzilov.todoApplication.entity.TasksEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TasksRepo extends JpaRepository<TasksEntity, Long> {
    @Modifying
    @Query("delete from TasksEntity t where t.status = true")
    void deleteAllWithTrueStatus();
}
