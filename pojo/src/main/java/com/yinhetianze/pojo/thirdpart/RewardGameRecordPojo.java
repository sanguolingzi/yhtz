package com.yinhetianze.pojo.thirdpart;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "reward_game_record")
public class RewardGameRecordPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 游戏id
     */
    @Column(name = "game_id")
    private Integer gameId;

    /**
     * 用户id
     */
    @Column(name = "customer_id")
    private Integer customerId;

    /**
     * 奖励游戏币
     */
    @Column(name = "game_amount")
    private BigDecimal gameAmount;

    /**
     * 奖励币流水记录id
     */
    @Column(name = "bankroll_serial")
    private Integer bankrollSerial;

    /**
     * 游戏任务id
     */
    @Column(name = "game_task_id")
    private Integer gameTaskId;

    /**
     * 游戏类型id
     */
    @Column(name = "game_type_id")
    private Integer gameTypeId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 接口执行结果
     */
    @Column(name = "judge")
    private Integer judge;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取游戏id
     *
     * @return game_id - 游戏id
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * 设置游戏id
     *
     * @param gameId 游戏id
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    /**
     * 获取用户id
     *
     * @return customer_id - 用户id
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 设置用户id
     *
     * @param customerId 用户id
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取奖励游戏币
     *
     * @return game_amount - 奖励游戏币
     */
    public BigDecimal getGameAmount() {
        return gameAmount;
    }

    /**
     * 设置奖励游戏币
     *
     * @param gameAmount 奖励游戏币
     */
    public void setGameAmount(BigDecimal gameAmount) {
        this.gameAmount = gameAmount;
    }

    /**
     * 获取奖励币流水记录id
     *
     * @return bankroll_serial - 奖励币流水记录id
     */
    public Integer getBankrollSerial() {
        return bankrollSerial;
    }

    /**
     * 设置奖励币流水记录id
     *
     * @param bankrollSerial 奖励币流水记录id
     */
    public void setBankrollSerial(Integer bankrollSerial) {
        this.bankrollSerial = bankrollSerial;
    }

    /**
     * 获取游戏任务id
     *
     * @return game_task_id - 游戏任务id
     */
    public Integer getGameTaskId() {
        return gameTaskId;
    }

    /**
     * 设置游戏任务id
     *
     * @param gameTaskId 游戏任务id
     */
    public void setGameTaskId(Integer gameTaskId) {
        this.gameTaskId = gameTaskId;
    }

    /**
     * 获取游戏类型id
     *
     * @return game_type_id - 游戏类型id
     */
    public Integer getGameTypeId() {
        return gameTypeId;
    }

    /**
     * 设置游戏类型id
     *
     * @param gameTypeId 游戏类型id
     */
    public void setGameTypeId(Integer gameTypeId) {
        this.gameTypeId = gameTypeId;
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

    public Integer getJudge(){
        return judge;
    }

    public void setJudge(Integer judge){
        this.judge = judge;
    }
}