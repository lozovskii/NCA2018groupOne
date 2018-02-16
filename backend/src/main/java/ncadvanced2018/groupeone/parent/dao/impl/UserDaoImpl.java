package ncadvanced2018.groupeone.parent.dao.impl;

import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.TimestampExtractor;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import ncadvanced2018.groupeone.parent.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDaoImpl implements UserDao {

    private NamedParameterJdbcOperations jdbcTemplate;

    private SimpleJdbcInsert userInsert;

    private UserWithDetailExtractor userWithDetailExtractor;

    private QueryService queryService;
    private AddressDao addressDao;

    @Autowired
    public UserDaoImpl(QueryService queryService, AddressDao addressDao) {
        this.queryService = queryService;
        this.addressDao = addressDao;
    }

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.userInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.userWithDetailExtractor = new UserWithDetailExtractor();
    }

    @Override
    public User create(User user) {
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("password", user.getPassword())
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("phone_number", user.getPhoneNumber())
                .addValue("email", user.getEmail())
                .addValue("address_id", Objects.isNull(user.getAddress()) ? null : user.getAddress().getId())
                .addValue("manager_id", Objects.isNull(user.getManager()) ? null : user.getAddress().getId())
                .addValue("registration_date", user.getRegistrationDate());

        Long newId = userInsert.executeAndReturnKey(sqlParameters).longValue();
        user.setId(newId);
        return user;

    }

    @Override
    public User findByEmail(String email) {
        String findUserByEmailQuery = queryService.getQuery("users.findByEmail");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("email", email);
        List<User> users = jdbcTemplate.query(findUserByEmailQuery, parameterSource, userWithDetailExtractor);
        return users.get(0);
    }

    @Override
    public User findById(Long id) {
        String findUserByIdQuery = queryService.getQuery("user.findById");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<User> users = jdbcTemplate.query(findUserByIdQuery, parameterSource, userWithDetailExtractor);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public boolean deleteByEmail(String email) {
        String deleteByEmail = queryService.getQuery("users.deleteByEmail");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("email", email);
        int deletedRows = jdbcTemplate.update(deleteByEmail, parameterSource);
        return deletedRows > 0;
    }

    @Override
    public boolean update(User user) {
        String update = queryService.getQuery("user.update");
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("password", user.getPassword())
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("phone_number", user.getPhoneNumber())
                .addValue("email", user.getEmail())
                .addValue("address_id", Objects.isNull(user.getAddress()) ? null : user.getAddress().getId())
                .addValue("manager_id", Objects.isNull(user.getManager()) ? null : user.getManager().getId())
                .addValue("registration_date", user.getRegistrationDate());
        int updatedRows = jdbcTemplate.update(update, sqlParameters);
        return updatedRows > 0;
    }

    @Override
    public boolean delete(User user) {
        return delete(user.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteById = queryService.getQuery("user.deleteById");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = jdbcTemplate.update(deleteById, mapSqlParameterSource);
        return deletedRows > 0;
    }

    private final class UserWithDetailExtractor implements ResultSetExtractor<List<User>>, TimestampExtractor {

        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new RealUser();
                user.setId(rs.getLong("id"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));
                user.setManager(UserDaoImpl.this.findById(rs.getLong("manager_id")));
                user.setAddress(UserDaoImpl.this.addressDao.findById(rs.getLong("address_id")));
                user.setRegistrationDate(getLocalDate(rs.getTimestamp("registration_date")));
                users.add(user);
            }
            return users;
        }
    }

}