package com.game.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "race")
    @Enumerated(EnumType.STRING)
    private Race race;

    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    private Profession profession;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "level")
    private Integer level;

    @Column(name = "untilNextLevel")
    private Integer untilNextLevel;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "banned")
    private Boolean banned;

    public Player() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        //this.level = level;
        this.level = (int) (((Math.sqrt((2500 + 200 * this.experience.doubleValue()))) - 50)/100);
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        //this.untilNextLevel = untilNextLevel;
        this.untilNextLevel = 50 * (this.level + 1) * (this.level + 2) - this.experience;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean isBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public String checkPlayersData(boolean checkNull){
        StringBuilder textError = new StringBuilder();
        if (name == null && checkNull){
            textError.append("name is null -");
        }else if (name != null && name.length() > 12){
            textError.append("name is over length -");
        }

        if (title == null && checkNull){
            textError.append("title is null -");
        }else if (title != null && title.length() > 30){
            textError.append("title is over length -");
        }

        if (race == null && checkNull){
            textError.append("race is null -");
        }

        if (profession == null && checkNull){
            textError.append("profession is null -");
        }

        if (birthday == null && checkNull){
            textError.append("birthday is null -");
        }else if(birthday != null && birthday.getTime() < 0){
            textError.append("birthday is < 0> -");
        }else if (birthday != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
            int year = Integer.parseInt(simpleDateFormat.format(birthday));
            if (year < 2000 || year > 3000)
                textError.append("birthday is out range 2000..3000 -");
        }

        if (experience == null && checkNull){
            textError.append("experience is null\n");
        }else if ((experience != null) && (experience < 0 || experience > 10000000)){
            textError.append("experience iis out range 0..10000000 -");
        }


        return textError.length() > 0 ? textError.toString() : null;
    }

    public void updateData(Player player){
        if (player.name != null) name = player.name;
        if (player.title!= null) title = player.title;
        if (player.race != null) race = player.race;
        if (player.profession != null) profession = player.profession;
        if (player.experience != null) experience = player.experience;
        if (player.birthday != null) birthday = player.birthday;
        if (player.banned != null) banned = player.banned;
        setLevel(null);
        setUntilNextLevel(null);
        if (isBanned() == null)
            setBanned(false);
    }
}
