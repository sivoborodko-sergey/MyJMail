package jmail.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "letters")
@NamedQuery(name = "All_letters", query = "FROM Letter")
public class Letter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id")
    private int id;
    private String title;
    private String body;

    @ManyToOne//(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "to_user", referencedColumnName = "user_id")
    private User to;

    @ManyToOne//(cascade = {CascadeType.ALL})
    @JoinColumn(name = "from_user", referencedColumnName = "user_id")
    private User from;
    @Column(name = "send_date")
    private Timestamp date;

    public Letter(){}

    public Letter(int id, String title, String body, User to, User from, Timestamp date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.to = to;
        this.from = from;
        this.date = date;
    }

    public Letter(String title, String body, User to, User from, Timestamp date) {
        this.title = title;
        this.body = body;
        this.to = to;
        this.from = from;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Timestamp getTimestamp() {
        return date;
    }

    public void setTimestamp(Timestamp date) {
        this.date = date;
    }

    public static List<Letter> searchByKeyWord(List<Letter> letters, String keyWord) {
        List<Letter> list = new ArrayList<>();
        for (Letter letter : letters) {
            String body = letter.getBody();
            if(IsEmptykeyWord(body, keyWord)) {
                list.add(letter);
            }
        }
        return list;
    }

    private static boolean IsEmptykeyWord(String body, String key) {
        char[] mas_body = body.toLowerCase().toCharArray();
        char[] mas_key = key.toLowerCase().toCharArray();
        int k = 0;
        for(int i = 0; i < mas_body.length; i++) {
            if(mas_body[i] == mas_key[k]) {
                if(helpIsEmpty(mas_body, mas_key, i, k, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean helpIsEmpty(char[] body, char[] key, int i, int j, int count) {
        if(i < body.length && j < key.length) {
            if(body[i] == key[j]) {
                return helpIsEmpty(body, key, ++i, ++j, ++count);
            } else {
                return false;
            }
        } else {
            if(count == key.length) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public synchronized String toString() {
        return "Letter{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", to=" + to +
                ", from=" + from +
                ", date=" + date +
                '}';
    }
}
