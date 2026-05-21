package com.example.chocottomemolocal.memo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.chocottomemolocal.memo.entity.FolderEntity;
import com.example.chocottomemolocal.memo.query.FolderMemoRow;

@Mapper
public interface FolderMapper {

    @Select("""
        SELECT 
            f.id,
            f.name,
            f.created_at,
            f.updated_at,
            f.update_count
        FROM
            folders AS f
    """)
    List<FolderEntity> findAll();

    @Select("""
        SELECT 
            f.id,
            f.name,
            f.created_at,
            f.updated_at,
            f.update_count
        FROM
            folders AS f
        WHERE
            f.id = #{id}
    """)
    FolderEntity findById(@Param("id") long id);

    @Select("""
        SELECT
            f.id AS folder_id,
            f.name AS folder_name,
            f.created_at AS folder_created_at,
            f.updated_at AS folder_updated_at,
            m.id AS memo_id,
            m.title AS memo_title,
            m.created_at AS memo_created_at,
            m.updated_at AS memo_updated_at
        FROM
            folders AS f
        LEFT JOIN
            folder_memo AS fm
            ON fm.folder_id = f.id
        LEFT JOIN
            memos AS m
            ON m.id = fm.memo_id
            AND m.is_deleted = false
        WHERE
            f.id = #{folderId}
        ORDER BY
            m.updated_at DESC
    """)
    List<FolderMemoRow> findMemosById(@Param("folderId") long folderId);

    @Insert("""
        INSERT INTO folders (
            name,
            created_at,
            updated_at,
            update_count
        )
        VALUES (
            #{name},
            CURRENT_TIMESTAMP,
            CURRENT_TIMESTAMP,
            0
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long save(FolderEntity folder);

    @Update("""
        UPDATE 
            folders AS f
        SET
            f.name = #{name},
            f.updated_at = CURRENT_TIMESTAMP,
            f.update_count = f.update_count + 1
        WHERE
            f.id = #{id}
            AND f.update_count = #{updateCount}
    """)
    int update(FolderEntity folder);

    @Delete("""
        DELETE FROM
            folders AS f
        WHERE
            f.id = #{id}
            AND f.update_count = #{updateCount}
    """)
    int delete(long id, long updateCount);
}
