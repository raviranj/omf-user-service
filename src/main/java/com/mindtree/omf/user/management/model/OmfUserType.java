/*
 * package com.mindtree.omf.user.management.model;
 * 
 * import javax.persistence.Column; import javax.persistence.Entity; import
 * javax.persistence.GeneratedValue; import javax.persistence.GenerationType;
 * import javax.persistence.Id; import javax.persistence.OneToOne; import
 * javax.persistence.Table;
 * 
 * import lombok.Data; import lombok.Getter; import lombok.Setter;
 * 
 * @Data
 * 
 * @Entity
 * 
 * @Table(name = "USER_TYPE_INFO") public class OmfUserType {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.AUTO)
 * 
 * @Column(name = "userTypeId") private Long userTypeId;
 * 
 * @Column(name = "type") private String type;
 * 
 * @OneToOne(mappedBy = "omfUserType") private OmfUser user; }
 */