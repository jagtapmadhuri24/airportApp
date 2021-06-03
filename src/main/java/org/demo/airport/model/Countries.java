package org.demo.airport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Countries {

    private String id;
    private String code;
    private String name;
    private String continent;
    private String wikipedia_link;
    private String keywords;
}
