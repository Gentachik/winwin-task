package arturnikytenko.authapi.Repositories;

import arturnikytenko.authapi.Models.DataModel.DataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DataModelRepository extends JpaRepository<DataModel, UUID> {
    boolean existsByEmail(String email);

    Optional<DataModel> findByEmail(String email);
}
