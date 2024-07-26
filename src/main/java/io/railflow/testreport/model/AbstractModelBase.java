package io.railflow.testreport.model;

/**
 *
 * Encapsulating the common variables for the test models
 *
 * @author LiuYang
 *
 */
public abstract class AbstractModelBase {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
