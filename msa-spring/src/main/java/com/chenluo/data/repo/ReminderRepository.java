package com.chenluo.data.repo;

import com.chenluo.data.dto.Reminder;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends CrudRepository<Reminder, Long> {

    @Query("""
            WITH all_row as (select *, row_number() over (partition by outbox_msg_id) as rn
                             from reminder
                             where is_target = 1
                               and supplier_corp_id = :supplierCorpId
                               and type = 1
                             order by sort_col, outbox_msg_id
                             limit 20)
                        
            select * from all_row
            """)
    List<Reminder> findType1(String supplierCorpId);

    @Query("""
            WITH all_1_reminder as (select distinct outbox_msg_id
                                    from reminder
                                    where is_target = 1
                                      and supplier_corp_id = 'corp'
                                      and type = 1),
                 all_row as (select *, row_number() over (partition by outbox_msg_id) as rn
                             from reminder
                             where is_target = 1
                               and supplier_corp_id = 'corp'
                               and type = 2
                               and outbox_msg_id not in (select * from all_1_reminder)
                             order by sort_col, outbox_msg_id
                             limit 20)
            select *
            from all_row
            where rn = 1; 
            """)
    List<Reminder> findType2(String supplierCorpId);

    @Query("""
            WITH all_1_2_reminder as (select distinct outbox_msg_id
                                    from reminder
                                    where is_target = 1
                                      and supplier_corp_id = :supplierCorpId
                                      and type in (1,2)),
                 all_row as (select *, row_number() over (partition by outbox_msg_id) as rn
                             from reminder
                             where is_target = 1
                               and supplier_corp_id = :supplierCorpId
                               and type = 3
                               and outbox_msg_id not in (select * from all_1_2_reminder)
                             order by sort_col, outbox_msg_id
                             limit 20)
            select *
            from all_row
            where rn = 1;
            """)
    List<Reminder> findType3(String supplierCorpId);
}