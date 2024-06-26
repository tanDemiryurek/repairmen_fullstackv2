package com.group21.repairmenService.entity;

import com.group21.repairmenService.dto.AdDTO;
import com.group21.repairmenService.enums.AdStatus;
import com.group21.repairmenService.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ads")
@Data
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private String description;
    private Double price;

    private AdStatus status = AdStatus.PENDING;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public AdDTO getAdDto(){
        AdDTO adDTO = new AdDTO();

        adDTO.setId(id);
        adDTO.setServiceName(serviceName);
        adDTO.setDescription(description);
        adDTO.setPrice(price);
        adDTO.setCompanyName(user.getName());
        adDTO.setReturnedImg(img);
        adDTO.setAdStatus(this.status);

        return adDTO;
    }
}
