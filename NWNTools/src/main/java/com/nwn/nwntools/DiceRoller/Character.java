
package com.nwn.nwntools.DiceRoller;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Caitlin
 */
public class Character {
    
    private String name;
    private String login;
    private String forum;
    private String tags;

    private String classes;
    private String alignment;
    private String race;
    private String origin;
    private String religion;
    private String description;
    
    private Abilities abilities;
    private Saves saves;
    private Skills skills;

    public String getName() {
        return validate(name);
    }

    @XmlElement(name = "Name")
    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return validate(login);
    }

    @XmlElement(name = "Login")
    public void setLogin(String login) {
        this.login = login;
    }

    public String getForum() {
        return validate(forum);
    }

    @XmlElement(name = "Forum")
    public void setForum(String forum) {
        this.forum = forum;
    }

    public String getTags() {
        return validate(tags);
    }

    @XmlElement(name = "Tags")
    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getClasses() {
        return validate(classes);
    }

    @XmlElement(name = "Classes")
    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getAlignment() {
        return validate(alignment);
    }

    @XmlElement(name = "Alignment")
    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getRace() {
        return validate(race);
    }

    @XmlElement(name = "Race")
    public void setRace(String race) {
        this.race = race;
    }

    public String getOrigin() {
        return validate(origin);
    }

    @XmlElement(name = "Origin")
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getReligion() {
        return validate(religion);
    }

    @XmlElement(name = "Religion")
    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getDescription() {
        return validate(description);
    }

    @XmlElement(name = "Description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Abilities getAbilities() {
        if (abilities == null) {
            abilities = new Abilities();
        }
        return abilities;
    }

    @XmlElement(name = "Abilities")
    public void setAbilities(Abilities abilities) {
        this.abilities = abilities;
    }

    public Saves getSaves() {
        if (saves == null) {
            saves = new Saves();
        }
        return saves;
    }

    @XmlElement(name = "Saves")
    public void setSaves(Saves saves) {
        this.saves = saves;
    }

    public Skills getSkills() {
        if (skills == null) {
            skills = new Skills();
        }
        return skills;
    }

    @XmlElement(name = "Skills")
    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Character{" + "name=" + name + ", login=" + login + ", forum=" + forum + ", tags=" + tags + ", classes=" + classes + ", alignment=" + alignment + ", race=" + race + ", origin=" + origin + ", religion=" + religion + ", description=" + description + ", abilities=" + abilities + ", saves=" + saves + ", skills=" + skills + '}';
    }
    
    
    
    public static class Abilities {
        
        private String strength;
        private String dexterity;
        private String constitution;
        private String wisdom;
        private String intelligence;
        private String charisma;

        public String getStrength() {
            return validateNumber(strength);
        }

        @XmlElement(name = "Strength")
        public void setStrength(String strength) {
            this.strength = strength;
        }

        public String getDexterity() {
            return validateNumber(dexterity);
        }

        @XmlElement(name = "Dexterity")
        public void setDexterity(String dexterity) {
            this.dexterity = dexterity;
        }

        public String getConstitution() {
            return validateNumber(constitution);
        }

        @XmlElement(name = "Constitution")
        public void setConstitution(String constitution) {
            this.constitution = constitution;
        }

        public String getWisdom() {
            return validateNumber(wisdom);
        }

        @XmlElement(name = "Wisdom")
        public void setWisdom(String wisdom) {
            this.wisdom = wisdom;
        }

        public String getIntelligence() {
            return validateNumber(intelligence);
        }

        @XmlElement(name = "Intelligence")
        public void setIntelligence(String intelligence) {
            this.intelligence = intelligence;
        }

        public String getCharisma() {
            return validateNumber(charisma);
        }

        @XmlElement(name = "Charisma")
        public void setCharisma(String charisma) {
            this.charisma = charisma;
        }

        @Override
        public String toString() {
            return "Abilities{" + "strength=" + strength + ", dexterity=" + dexterity + ", constitution=" + constitution + ", wisdom=" + wisdom + ", intelligence=" + intelligence + ", charisma=" + charisma + '}';
        }

        
    }
    
    public static class Saves {
        
        private String fortitude;
        private String reflex;
        private String will;

        public String getFortitude() {
            return validateNumber(fortitude);
        }

        @XmlElement(name = "Fortitude")
        public void setFortitude(String fortitude) {
            this.fortitude = fortitude;
        }

        public String getReflex() {
            return validateNumber(reflex);
        }

        @XmlElement(name = "Reflex")
        public void setReflex(String reflex) {
            this.reflex = reflex;
        }

        public String getWill() {
            return validateNumber(will);
        }

        @XmlElement(name = "Will")
        public void setWill(String will) {
            this.will = will;
        }

        @Override
        public String toString() {
            return "Saves{" + "fortitude=" + fortitude + ", reflex=" + reflex + ", will=" + will + '}';
        }
        
        

    }

    public static class Skills {
        
        private String hide;
        private String moveSilently;
        private String spot;
        private String listen;
        private String search;
        private String perform;
        private String concentration;
        private String parry;
        private String lore;
        private String spellcraft;
        private String bluff;
        private String persuade;
        private String slightOfHand;
        private String discipline;
        private String antagonize;
        private String pickLock;

        public String getHide() {
            return validateNumber(hide);
        }

        @XmlElement(name = "Hide")
        public void setHide(String hide) {
            this.hide = hide;
        }

        public String getMoveSilently() {
            return validateNumber(moveSilently);
        }

        @XmlElement(name = "MoveSilently")
        public void setMoveSilently(String moveSilently) {
            this.moveSilently = moveSilently;
        }

        public String getSpot() {
            return validateNumber(spot);
        }

        @XmlElement(name = "Spot")
        public void setSpot(String spot) {
            this.spot = spot;
        }

        public String getListen() {
            return validateNumber(listen);
        }

        @XmlElement(name = "Listen")
        public void setListen(String listen) {
            this.listen = listen;
        }

        public String getSearch() {
            return validateNumber(search);
        }

        @XmlElement(name = "Search")
        public void setSearch(String search) {
            this.search = search;
        }

        public String getPerform() {
            return validateNumber(perform);
        }

        @XmlElement(name = "Perform")
        public void setPerform(String perform) {
            this.perform = perform;
        }

        public String getConcentration() {
            return validateNumber(concentration);
        }

        @XmlElement(name = "Concentration")
        public void setConcentration(String concentration) {
            this.concentration = concentration;
        }

        public String getParry() {
            return validateNumber(parry);
        }

        @XmlElement(name = "Parry")
        public void setParry(String parry) {
            this.parry = parry;
        }

        public String getLore() {
            return validateNumber(lore);
        }

        @XmlElement(name = "Lore")
        public void setLore(String lore) {
            this.lore = lore;
        }

        public String getSpellcraft() {
            return validateNumber(spellcraft);
        }

        @XmlElement(name = "Spellcraft")
        public void setSpellcraft(String spellcraft) {
            this.spellcraft = spellcraft;
        }

        public String getBluff() {
            return validateNumber(bluff);
        }

        @XmlElement(name = "Bluff")
        public void setBluff(String bluff) {
            this.bluff = bluff;
        }

        public String getPersuade() {
            return validateNumber(persuade);
        }

        @XmlElement(name = "Persuade")
        public void setPersuade(String persuade) {
            this.persuade = persuade;
        }

        public String getSlightOfHand() {
            return validateNumber(slightOfHand);
        }

        @XmlElement(name = "SlightOfHand")
        public void setSlightOfHand(String slightOfHand) {
            this.slightOfHand = slightOfHand;
        }

        public String getDiscipline() {
            return validateNumber(discipline);
        }

        @XmlElement(name = "Discipline")
        public void setDiscipline(String discipline) {
            this.discipline = discipline;
        }

        public String getAntagonize() {
            return validateNumber(antagonize);
        }

        @XmlElement(name = "Antagonize")
        public void setAntagonize(String antagonize) {
            this.antagonize = antagonize;
        }

        public String getPickLock() {
            return validateNumber(pickLock);
        }

        @XmlElement(name = "PickLock")
        public void setPickLock(String pickLock) {
            this.pickLock = pickLock;
        }

        @Override
        public String toString() {
            return "Skills{" + "hide=" + hide + ", moveSilently=" + moveSilently + ", spot=" + spot + ", listen=" + listen + ", search=" + search + ", perform=" + perform + ", concentration=" + concentration + ", parry=" + parry + ", lore=" + lore + ", spellcraft=" + spellcraft + ", bluff=" + bluff + ", persuade=" + persuade + ", slightOfHand=" + slightOfHand + ", discipline=" + discipline + ", antagonize=" + antagonize + ", pickLock=" + pickLock + '}';
        }
        
        
 
    }
    
    private String validate(String value) {
        if (value == null) {
            return "";
        } else {
            return value;
        }
    }
    
    private static String validateNumber(String value) {
        if (value == null) {
            return "0";
        } else {
            return value;
        }
    }
}
