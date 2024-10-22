package ru.tbank.tflowers.component.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<ComponentEntity, Long> {
}
