package com.ercanbeyen.springbootfirstrestapi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass // All the fields of the BaseEntity are extended
@Setter
@Getter
@ToString
public class BaseEntity implements Serializable { // we may transport the object through network or read/write the object from/to disc
    private Date latestChangeAt;
    private String latestChangeBy;
}
