package ncadvanced2018.groupeone.parent.model.entity;

import lombok.Data;

@Data
public class Office {

    private Long id;
    private Address address;
    private String name;
    private String description;

}
