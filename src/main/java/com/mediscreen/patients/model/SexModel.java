package com.mediscreen.patients.model;

import javax.persistence.*;

@Entity
@Table(name = "sex")
public class SexModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "id")
    private PatientModel patientSex;

    @Column(name="sex")
    private String sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

//   @JoinColumn(name="user_id", foreignKey = @ForeignKey(name = "fk_bank_user"))
}
