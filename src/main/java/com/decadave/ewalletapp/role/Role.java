package com.decadave.ewalletapp.role;

import com.decadave.ewalletapp.shared.BaseClass;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role extends BaseClass {
    private String name;
}
