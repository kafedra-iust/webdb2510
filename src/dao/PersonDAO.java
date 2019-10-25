package dao;

import logic.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonDAO {
    private DataSource ds;

    public PersonDAO(DataSource ds) {
        this.ds = ds;
    }

    public List<Person> getPeople() {
        try (Connection connection = ds.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from person");
            ResultSet rs = ps.executeQuery();
            List<Person> people = new ArrayList<>();
            while (rs.next()) {
                people.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("age")));
            }
            return people;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    public void addPerson(String name, int age) {
        try (Connection connection = ds.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("insert into person (name, age) values (?,?)");
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
