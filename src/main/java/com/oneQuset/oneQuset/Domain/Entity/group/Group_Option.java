package com.oneQuset.oneQuset.Domain.Entity.group;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Group_Option {
    @Id
    @GeneratedValue
    private Long gc_number;

}
