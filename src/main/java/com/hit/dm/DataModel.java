package com.hit.dm;

import java.io.Serializable;

/**
 * A container Class - obtaining id and content.
 *
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
     * Get content
     *
     * @return -  The current content.
     */
    public T getContent() {
        return content;
    }

    /**
     * Set content
     *
     * @param content - given content to set as the current content.
     */
    public void setContent(T content) {
        this.content = content;
    }

    /**
     * Get id
     *
     * @return - The current id
     */
    public Long getDataModelId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id - given id to set as the current id.
     */
    public void setDataModelId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return (this.id.equals(((DataModel<T>) obj).getDataModelId())
                && this.content.equals(((DataModel<T>) obj).getContent()));
    }

    @Override
    public String toString() {
        return this.id + " " + this.content.toString();
    }
}