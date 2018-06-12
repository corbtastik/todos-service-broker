package io.corbs.todos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodosServiceBindingRepo extends JpaRepository<TodosServiceBinding, String> {
}
