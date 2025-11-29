package de.iu.ghostnet.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GhostNet")
public class GhostNet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double latitude;
    private double longitude;

    @Column(name = "estimated_size")
    private int estimatedSize;

    @Enumerated(EnumType.ORDINAL)
    private GhostNetStatus status;

    @Column(name = "reporter_name")
    private String reporterName;

    @Column(name = "reporter_phone")
    private String reporterPhone;

    @Column(name = "rescuer_name")
    private String rescuerName;

    @Column(name = "rescuer_phone")
    private String rescuerPhone;

    public GhostNet() {
        this.status = GhostNetStatus.GEMELDET;
    }

    // Getter und Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public int getEstimatedSize() { return estimatedSize; }
    public void setEstimatedSize(int estimatedSize) { this.estimatedSize = estimatedSize; }

    public GhostNetStatus getStatus() { return status; }
    public void setStatus(GhostNetStatus status) { this.status = status; }

    public String getReporterName() { return reporterName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }

    public String getReporterPhone() { return reporterPhone; }
    public void setReporterPhone(String reporterPhone) { this.reporterPhone = reporterPhone; }

    public String getRescuerName() { return rescuerName; }
    public void setRescuerName(String rescuerName) { this.rescuerName = rescuerName; }

    public String getRescuerPhone() { return rescuerPhone; }
    public void setRescuerPhone(String rescuerPhone) { this.rescuerPhone = rescuerPhone; }
}