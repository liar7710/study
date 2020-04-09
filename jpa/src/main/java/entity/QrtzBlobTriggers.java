package entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "qrtz_blob_triggers")
@Data
@Entity
public class QrtzBlobTriggers implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private primary_key id;

    @Column(name = "BLOB_DATA")
    private byte[] blobData;

    @Embeddable
    @Data
    static class primary_key implements Serializable {
        @Column(name = "SCHED_NAME", insertable = false, nullable = false)
        private String schedName;

        @Column(name = "TRIGGER_GROUP", insertable = false, nullable = false)
        private String triggerGroup;

        @Column(insertable = false, name = "TRIGGER_NAME", nullable = false)
        private String triggerName;
    }
}
