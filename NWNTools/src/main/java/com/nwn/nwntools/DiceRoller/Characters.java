
package com.nwn.nwntools.DiceRoller;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Caitlin
 */
@XmlRootElement(name = "Characters")
public class Characters {
    
    private List<Character> characters;

    public List<Character> getCharacters() {
        if (characters == null) {
            characters = new ArrayList<>();
        }
        return characters;
    }

    @XmlElement(name = "Character")
    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

}
