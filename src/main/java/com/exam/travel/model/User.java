package com.exam.travel.model;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.accountId
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    private String accountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.userName
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.token
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    private String token;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.gmtCreate
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.userPassword
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    private String userPassword;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.userType
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    private Integer userType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.avatarUrl
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    private String avatarUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.userTelephone
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    private String userTelephone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.userEmail
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    private String userEmail;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.accountId
     *
     * @return the value of user.accountId
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.accountId
     *
     * @param accountId the value for user.accountId
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.userName
     *
     * @return the value of user.userName
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.userName
     *
     * @param userName the value for user.userName
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.token
     *
     * @return the value of user.token
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.token
     *
     * @param token the value for user.token
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.gmtCreate
     *
     * @return the value of user.gmtCreate
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.gmtCreate
     *
     * @param gmtCreate the value for user.gmtCreate
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.userPassword
     *
     * @return the value of user.userPassword
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.userPassword
     *
     * @param userPassword the value for user.userPassword
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.userType
     *
     * @return the value of user.userType
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.userType
     *
     * @param userType the value for user.userType
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.avatarUrl
     *
     * @return the value of user.avatarUrl
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.avatarUrl
     *
     * @param avatarUrl the value for user.avatarUrl
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.userTelephone
     *
     * @return the value of user.userTelephone
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public String getUserTelephone() {
        return userTelephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.userTelephone
     *
     * @param userTelephone the value for user.userTelephone
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone == null ? null : userTelephone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.userEmail
     *
     * @return the value of user.userEmail
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.userEmail
     *
     * @param userEmail the value for user.userEmail
     *
     * @mbg.generated Sun Mar 15 12:18:01 CST 2020
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }
}