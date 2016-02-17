/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nwn.nwntools.NoteKeeper;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Caitlin
 */
@XmlRootElement(name = "Notes")
public class Notes {
    
    private List<Note> notes;
    private List<String> allTags;

    public List<Note> getNotes() {
        if (notes == null) {
            notes = new ArrayList<>();
        }
        return notes;
    }

    @XmlElement(name = "Note")
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<String> getAllTags() {
        if (allTags == null) {
            allTags = new ArrayList<>();
        }
        return allTags;
    }

    @XmlElementWrapper(name = "AllTags")
    @XmlElement(name = "Tag")
    public void setAllTags(List<String> allTags) {
        this.allTags = allTags;
    }

    @Override
    public String toString() {
        return "Notes{" + "notes=" + notes + ", allTags=" + allTags + '}';
    }
}
