package ncadvanced2018.groupeone.parent.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAddress;

@JsonDeserialize(as = RealAddress.class)
public interface Address {

    Long getId();

    void setId(Long id);

    String getStreet();

    void setStreet(String street);

    String getHouse();

    void setHouse(String house);

    Integer getFloor();

    void setFloor(Integer floor);

    Integer getFlat();

    void setFlat(Integer flat);
}