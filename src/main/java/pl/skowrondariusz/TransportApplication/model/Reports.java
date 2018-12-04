package pl.skowrondariusz.TransportApplication.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;
import pl.skowrondariusz.TransportApplication.config.DistanceSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Reports {



    @Column(name = "id")
    @Id
    @GeneratedValue
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    @JsonSerialize(using=DistanceSerializer.class)
    private Long totalDistance;
//    @JsonSerialize(using=PriceSerializer.class)
    private Long totalPrice;


    public Reports(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Reports() {

    }


//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public LocalDate getStartDate() {
//        return startDate;
//    }
//
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
//
//    public LocalDate getEndDate() {
//        return endDate;
//    }
//
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Long totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Reports{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalDistance=" + totalDistance + "km" +
                ", totalPrice=" + totalPrice +
                '}';
    }

}
