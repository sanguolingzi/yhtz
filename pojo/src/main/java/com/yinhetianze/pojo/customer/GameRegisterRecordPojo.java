package com.yinhetianze.pojo.customer;

import java.util.Date;
import javax.persistence.*;

@Table(name = "game_register_record")
public class GameRegisterRecordPojo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 推荐人gameId
     */
    @Column(name = "p_game_id")
    private Integer pGameId;

    /**
     * 被推荐人gameId
     */
    @Column(name = "game_id")
    private Integer gameId;

    /**
     * 是否成功 (0不成功 1成功)
     */
    private Byte status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取推荐人gameId
     *
     * @return p_game_id - 推荐人gameId
     */
    public Integer getpGameId() {
        return pGameId;
    }

    /**
     * 设置推荐人gameId
     *
     * @param pGameId 推荐人gameId
     */
    public void setpGameId(Integer pGameId) {
        this.pGameId = pGameId;
    }

    /**
     * 获取被推荐人gameId
     *
     * @return game_id - 被推荐人gameId
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * 设置被推荐人gameId
     *
     * @param gameId 被推荐人gameId
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    /**
     * 获取是否成功 (0不成功 1成功)
     *
     * @return status - 是否成功 (0不成功 1成功)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置是否成功 (0不成功 1成功)
     *
     * @param status 是否成功 (0不成功 1成功)
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}