package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Token;

@Repository
public interface TokenDao extends JpaRepository<Token, Integer> {

    Token findByToken(String token);

    @Query("from Token t where t.heartbeatTime + :tokenValidTime < unix_timestamp(now())*1000")
    List<Token> findInvalidToken(@Param("tokenValidTime") long tokenValidTime);
}
