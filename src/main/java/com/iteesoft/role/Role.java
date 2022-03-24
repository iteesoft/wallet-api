package com.iteesoft.role;

import com.iteesoft.shared.BaseClass;
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
