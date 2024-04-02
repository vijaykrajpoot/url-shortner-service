package com.url.shortner.url.shortner.service.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "URL")
public class URLEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    private String sortURL;

    private String longURL;

    public Long getId() {
        return id;
    }

    public String getSortURL() {
        return sortURL;
    }

    public void setSortURL(String sortURL) {
        this.sortURL = sortURL;
    }

    public String getLongURL() {
        return longURL;
    }

    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }
}