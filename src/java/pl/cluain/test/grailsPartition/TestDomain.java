package pl.cluain.test.grailsPartition;


import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "some_table")
@SQLInsert(sql = "INSERT INTO some_table (date_created, some_date, version, id) VALUES (?,?,?,?)", check = ResultCheckStyle.NONE)
@NamedQueries(value = {
        @NamedQuery(name = "TestDomain.findAll",
                query = "SELECT a FROM TestDomain a"),
        @NamedQuery(name = "BattleLog.findAllBySomeDate",
                query = "SELECT a FROM TestDomain a WHERE a.someDate = :someDate")
})
public class TestDomain implements Serializable {
    private Long id;
    private Integer version;

    private Date someDate;
    private Date created;

    public TestDomain() {
        created = new Date();
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "version")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "date_created", nullable = false, columnDefinition = "date")
    @Temporal(TemporalType.DATE)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


    @Column(name = "some_date", columnDefinition = "date")
    @Temporal(TemporalType.DATE)
    public Date getSomeDate() {
        return someDate;
    }

    public void setSomeDate(Date someDate) {
        this.someDate = someDate;
    }

}
