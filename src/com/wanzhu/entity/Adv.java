package com.wanzhu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_adv")
public class Adv
{
    private String advid;
    private String memo;
    private String url;
    private int valid;
    private Integer parking;
    private Date lastuploadtime;
    private String link;
    
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "advid", unique = true, nullable = false, length = 32)
    public String getAdvid()
    {
        return advid;
    }
    public void setAdvid(String advid)
    {
        this.advid = advid;
    }
    @Column(name="memo" , nullable = false)
    public String getMemo()
    {
        return memo;
    }
    public void setMemo(String memo)
    {
        this.memo = memo;
    }
    @Column(name="url" , nullable = false)
    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    @Column(name="valid" , nullable = false)
    public int getValid()
    {
        return valid;
    }
    public void setValid(int valid)
    {
        this.valid = valid;
    }
    @Column(name="lastuploadtime",nullable=false)
    public Date getLastuploadtime()
    {
        return lastuploadtime;
    }
    public void setLastuploadtime(Date lastuploadtime)
    {
        this.lastuploadtime = lastuploadtime;
    }
    @Column(name="parking" , nullable = false)
    public Integer getParking()
    {
        return parking;
    }
    public void setParking(Integer parking)
    {
        this.parking = parking;
    }
    @Column(name="link",nullable=true)
    public String getLink()
    {
        return link;
    }
    public void setLink(String link)
    {
        this.link = link;
    }
    
    

}
