package com.zarbafian.company.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * A company that can have multiple beneficial owners.
 */
@Entity
@Table(name = "company")
@GenericGenerator(
        name = "company_seq",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "sequence_name", value = "company_id_seq"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1000"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
        }
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Company.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company {

    @Id
    @GeneratedValue(generator="company_seq")
    private Long id;

    /** name of the company */
    @Column(name = "name")
    private String name;

    /** address of the company */
    @Column(name = "address")
    private String address;

    /** city */
    @Column(name = "city")
    private String city;

    /** country */
    @Column(name = "country")
    private String country;

    /** email */
    @Column(name = "email")
    private String email;

    /** phone number */
    @Column(name = "phone_number")
    private String phoneNumber;

    /** beneficial owners of this company */
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE)
    @JoinTable(
            name = "company_owner",
            joinColumns = @JoinColumn(
                    name = "company_id",
                    referencedColumnName = "id") ,
            inverseJoinColumns = @JoinColumn(
                    name = "owner_id",
                    referencedColumnName = "id") )
    private List<Owner> owners;

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", owners=" + owners +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }
}
