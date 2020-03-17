package com.hawkore.test;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;

public class HawkoreEmployee implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6285166980064927716L;

    // Member fields

    @QuerySqlField(index = true, descending = false)
    @QueryTextField
    private final String id;
    @QuerySqlField(index = true)
    @QueryTextField
    private final String name;
    @QuerySqlField(index = true)
    @QueryTextField
    private final String surname;
    @QuerySqlField(index = true)
    @QueryTextField
    private final String email;
    @QuerySqlField(index = true)
    @QueryTextField
    private final String jobTitle;

    // Constructors

    public HawkoreEmployee(final String id, final String name, final String surname, final String email,
        final String jobTitle) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.jobTitle = jobTitle;
    }

    // Accessors

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    // toString & equals

    @Override
    public String toString() {
        return "HawkoreEmployee [id=" + this.getId() + ", name=" + this.getName() + ", surname=" + this.getSurname()
            + ", email=" + this.getEmail() + ", jobTitle=" + this.getJobTitle() + "]";
    }

    @Override
    public boolean equals(final Object obj) {
        return this.toString().equals(obj.toString());
    }

}
