package simple.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface UUIDMapper {
    String selectUUID();
    int selectUUID_Short();
}
