package com.xuyazhou.mynote.mapper;

import com.xuyazhou.mynote.model.MemberToken;
import com.xuyazhou.mynote.model.MemberTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberTokenMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_token
     *
     * @mbg.generated
     */
    long countByExample(MemberTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_token
     *
     * @mbg.generated
     */
    int deleteByExample(MemberTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_token
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_token
     *
     * @mbg.generated
     */
    int insert(MemberToken record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_token
     *
     * @mbg.generated
     */
    int insertSelective(MemberToken record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_token
     *
     * @mbg.generated
     */
    List<MemberToken> selectByExample(MemberTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_token
     *
     * @mbg.generated
     */
    MemberToken selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_token
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MemberToken record, @Param("example") MemberTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_token
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MemberToken record, @Param("example") MemberTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_token
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MemberToken record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_token
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MemberToken record);
}