package repostory;

import entity.QrtzBlobTriggers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QrtzBlobTriggersRepository extends JpaRepository<QrtzBlobTriggers, String>, JpaSpecificationExecutor<QrtzBlobTriggers> {

}