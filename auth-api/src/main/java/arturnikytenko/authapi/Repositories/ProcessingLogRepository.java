package arturnikytenko.authapi.Repositories;

import arturnikytenko.authapi.Models.ProcessingLog.ProcessingLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessingLogRepository extends JpaRepository<ProcessingLog, UUID> {
}
