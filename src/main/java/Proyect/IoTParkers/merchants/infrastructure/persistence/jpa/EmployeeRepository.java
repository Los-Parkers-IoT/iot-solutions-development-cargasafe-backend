package Proyect.IoTParkers.merchants.infrastructure.persistence.jpa;

import Proyect.IoTParkers.merchants.domain.model.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByMerchantId(Long merchantId);
}
