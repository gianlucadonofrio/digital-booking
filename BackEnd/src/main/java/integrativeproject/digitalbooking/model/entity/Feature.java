package integrativeproject.digitalbooking.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "features")

public class Feature {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
    private Integer id;
    private String feature;

   // private string imageURl; después veremos si se usa, por ahora del lado de front

}
