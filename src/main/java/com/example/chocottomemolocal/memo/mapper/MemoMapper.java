package com.example.chocottomemolocal.memo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.chocottomemolocal.memo.entity.MemoEntity;

@Mapper
public interface MemoMapper {
    @Select("""
        SELECT 
            m.id,
            m.title,
            m.content,
            m.is_deleted,
            m.created_at,
            m.updated_at,
            m.update_count
        FROM
            memo AS m
        WHERE
            m.is_deleted = false
    """)
    List<MemoEntity> findAll();

    @Select("""
        SELECT
            m.id,
            m.title,
            m.content,
            m.is_deleted,
            m.created_at,
            m.updated_at,
            m.update_count
        FROM 
            memo AS m 
        WHERE
            m.id = #{id}
            AND m.is_deleted = false
    """)
    MemoEntity findById(@Param("id") long id);

    @Insert("""
        INSERT INTO memo (
            title,
            content,
            is_deleted,
            created_at,
            updated_at,
            update_count
        )
        VALUES (
            #{title},
            #{content},
            false,
            UNIX_TIMESTAMP(),
            UNIX_TIMESTAMP(),
            0
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long save(MemoEntity memo);

    @Update("""
        UPDATE 
            memo AS m 
        SET 
            m.title = #{title},
            m.content = #{content},
            m.updated_at = UNIX_TIMESTAMP(),
            m.update_count = m.update_count + 1
        WHERE 
            m.id = #{id}
            AND m.update_count = #{updateCount}
    """)
    int update(MemoEntity memo);

    @Update("""
        UPDATE 
            memo AS m 
        SET 
            m.is_deleted = true,
            m.updated_at = UNIX_TIMESTAMP(),
            m.update_count = m.update_count + 1
        WHERE 
            m.id = #{id} 
            AND m.update_count = #{updateCount}
    """)
    int delete(@Param("id") long id, @Param("updateCount") long updateCount);
}
