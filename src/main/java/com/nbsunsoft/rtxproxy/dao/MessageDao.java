package com.nbsunsoft.rtxproxy.dao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nbsunsoft.rtxproxy.domain.Message;

@Repository("messageDao")
public class MessageDao {

    private static final String INSERT_SQL = " insert into t_message(id, gmt_create,  gmt_modified, title, msg, receiver, b_sent) values(?, ?, ?, ?, ?, ?, ?) ";
    private static final String DELETE_SQL = " delete from t_message where id=? ";
    private static final String UPDATE_SQL = " update t_message set gmt_create=?, gmt_modified=?, title=?, msg=?, receiver=?, b_sent=? where id=? ";
    private static final String QUERY_ONE_SQL = " select * from t_message where id=? ";
    private static final String QUERY_ALL_SQL = " select * from t_message ";
    private static final String COUNT_SQL = " select count(*) from t_message ";
    private static final String MAX_ID_SQL = " select max(id) from t_message ";
    private static final String MIN_ID_SQL = " select min(id) from t_message ";
    private static final String LONGEST_UNSEND_SQL = " select id, min(gmt_modified) from t_message where b_sent=0 ";

    private JdbcTemplate jdbcTemplate;

    public void insertMessage(Message message) {
        Object[] args = { message.getId(), message.getGmtCreate().toString(),
                message.getGmtModified().toString(), message.getTitle(), message.getMsg(),
                message.getReceiver(), message.getbSent() };
        jdbcTemplate.update(INSERT_SQL, args);
    }

    public void deleteMessage(Message message) {
        Object[] args = { message.getId() };
        jdbcTemplate.update(DELETE_SQL, args);
    }

    public void updateMessage(Message message) {
        Object[] args = { message.getGmtCreate().toString(),
                message.getGmtModified().toString(), message.getTitle(), message.getMsg(),
                message.getReceiver(), message.getbSent(), message.getId() };
        jdbcTemplate.update(UPDATE_SQL, args);
    }

    public Message getMessage(Integer id) {
        Message message = new Message();
        Object[] args = { id };
        jdbcTemplate.query(QUERY_ONE_SQL, args, rs -> {
            message.setId(rs.getInt("id"));
            message.setGmtCreate(Instant.parse(rs.getString("gmt_create")));
            message.setGmtModified(Instant.parse(rs.getString("gmt_modified")));
            message.setTitle(rs.getString("title"));
            message.setMsg(rs.getString("msg"));
            message.setReceiver(rs.getString("receiver"));
            message.setbSent(rs.getInt("b_sent"));
        });
        return message;
    }

    public List<Message> listMessage() {
        List<Message> messages = new ArrayList<>();
        jdbcTemplate.query(QUERY_ALL_SQL, rs -> {
            Message message = new Message();
            message.setId(rs.getInt("id"));
            message.setGmtCreate(Instant.parse(rs.getString("gmt_create")));
            message.setGmtModified(Instant.parse(rs.getString("gmt_modified")));
            message.setTitle(rs.getString("title"));
            message.setMsg(rs.getString("msg"));
            message.setReceiver(rs.getString("receiver"));
            message.setbSent(rs.getInt("b_sent"));
            messages.add(message);
        });
        return messages;
    }

    public int countMessage() {
        return jdbcTemplate.queryForObject(COUNT_SQL, Integer.class);
    }

    public Integer getMaxMessageId() {
        Message message = new Message();
        jdbcTemplate.query(MAX_ID_SQL, rs -> {
            message.setId(rs.getInt("max(id)"));
        });
        return message.getId();
    }

    public Integer getMinMessageId() {
        Message message = new Message();
        jdbcTemplate.query(MIN_ID_SQL, rs -> {
            message.setId(rs.getInt("min(id)"));
        });
        return message.getId();
    }
    
    public Integer getLongestUnsendMessageId() {
        Message message = new Message();
        jdbcTemplate.query(LONGEST_UNSEND_SQL, rs -> {
            message.setId(rs.getInt("id"));
        });
        return message.getId();
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
