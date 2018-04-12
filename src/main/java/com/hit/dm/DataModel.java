package com.hit.dm;

import java.io.Serializable;

/**
 * A container Class - obtaining id and content.
 * @param <T> -
 */
public class DataModel<T> implements Serializable {
    private Long id;
    private T content;

    public DataModel(Long id, T content) {
        this.id = id;
        this.content = content;
    }

    /**
     *
     * @param id
     * @return -  The a content of a given id.
     */
    public T getContent(Long id){
        return content;
    }

    /**
     * Sets a given content.
     * @param content - given content to set as the current content.
     */
    public void setContent(T content){
        this.content = content;
    }

    /**
     * Returns the current id.
     * @return
     */
    public Long getDataModelId() {
        return id;
    }

    /**
     * Sets a given id.
     * @param id - given id to set as the current id.
     */
    public void setDataModelId(Long id){
        this.id = id;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
