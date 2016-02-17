package com.nwn.nwntools.NoteKeeper;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Caitlin
 */
public class Note {

    private String tags;
    private String name;
    private String subject;
    private String description;
    
    
    public String getTags() {
        return validate(tags);
    }

    @XmlElement(name = "Tags")
    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getName() {
        return validate(name);
    }

    @XmlElement(name = "Name")
    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return validate(subject);
    }

    @XmlElement(name = "Subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return validate(description);
    }

    @XmlElement(name = "Description")
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean containsTags(String searchString) {
        return (tags.contains(searchString));
    }

    private String validate(String field) {
        if (field == null) {
            return "";
        } else {
            return field;
        }
    }


    @Override
    public String toString() {
        return "Note{" + "tags=" + tags + ", name=" + name + ", subject=" + subject + ", description=" + description + '}';
    }

}
