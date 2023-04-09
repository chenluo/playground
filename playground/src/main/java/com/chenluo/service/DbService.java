package com.chenluo.service;

import com.chenluo.db.UserDo;
import com.chenluo.db.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DbService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserRepo userRepo;

    @Autowired
    public DbService(NamedParameterJdbcTemplate namedParameterJdbcTemplate, UserRepo userRepo) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userRepo = userRepo;
    }

    //    @Transactional
    //    public void simpleTrans() throws SQLException {
    //
    //        StopWatch stopWatch = new StopWatch();
    //        for (int i = 0; i < 60; i++) {
    //            System.out.println(String.valueOf(i));
    //            if (i > 50) {
    //                throw new RuntimeException("manual exception");
    //            }
    //            Connection con = null;
    //            try {
    //                stopWatch.start("getConnection" + i);
    //                con = dbUtil.getDataSource().getConnection();
    //                stopWatch.stop();
    //                System.out.println(stopWatch.getLastTaskInfo().getTaskName() + ":" +
    //                        stopWatch.getLastTaskTimeMillis());
    //                stopWatch.start(String.valueOf(i));
    //                Statement st = con.createStatement();
    //
    //                int effectedRows =
    //                        st.executeUpdate(String.format("insert into testTbl(v) values(%d)",
    //                        i));
    //                st.close();
    //                stopWatch.stop();
    //                System.out.println(stopWatch.getLastTaskTimeMillis());
    //            } finally {
    //                if (con != null) {
    //                    try {
    //                        con.close();
    //                    } catch (Exception ignore) {
    //                    }
    //                }
    //            }
    //        }
    //    }

    //    @Transactional(rollbackFor = TransactionRuntimeException.class)
    @Transactional()
    public void execTransaction(int flag) {
        try {
            UserDo entity = new UserDo();
            entity.userId = "userid_" + flag;
            entity.password = "password";
            userRepo.save(entity);
            if (flag < 0) {
                throw new TransactionRuntimeException();
            }
        } catch (TransactionRuntimeException e) {
            System.out.println("exception caught.");
            throw new TransactionRuntimeException();
        }
    }

    @Transactional
    public void tran(int loopCount) {
        for (int i = 0; i < loopCount; i++) {
            if (i > 50) {
                throw new RuntimeException("manual exception.");
            }
            namedParameterJdbcTemplate.getJdbcOperations()
                    .update("insert into testTbl(v) values(?)", String.valueOf(i));
        }
    }
}
