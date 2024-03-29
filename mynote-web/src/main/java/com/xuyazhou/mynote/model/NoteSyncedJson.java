package com.xuyazhou.mynote.model;

import java.io.Serializable;

public class NoteSyncedJson implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column note_synced_json._id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column note_synced_json.note_sid
     *
     * @mbg.generated
     */
    private String noteSid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column note_synced_json.json
     *
     * @mbg.generated
     */
    private String json;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table note_synced_json
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column note_synced_json._id
     *
     * @return the value of note_synced_json._id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column note_synced_json._id
     *
     * @param id the value for note_synced_json._id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column note_synced_json.note_sid
     *
     * @return the value of note_synced_json.note_sid
     *
     * @mbg.generated
     */
    public String getNoteSid() {
        return noteSid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column note_synced_json.note_sid
     *
     * @param noteSid the value for note_synced_json.note_sid
     *
     * @mbg.generated
     */
    public void setNoteSid(String noteSid) {
        this.noteSid = noteSid == null ? null : noteSid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column note_synced_json.json
     *
     * @return the value of note_synced_json.json
     *
     * @mbg.generated
     */
    public String getJson() {
        return json;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column note_synced_json.json
     *
     * @param json the value for note_synced_json.json
     *
     * @mbg.generated
     */
    public void setJson(String json) {
        this.json = json == null ? null : json.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table note_synced_json
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", noteSid=").append(noteSid);
        sb.append(", json=").append(json);
        sb.append("]");
        return sb.toString();
    }
}