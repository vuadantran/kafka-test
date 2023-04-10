package org.datacho;

import java.io.Serializable;

public class PostCreateUserMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private Long storeId;
    private Long parentIntroId;
    private Long parentNetworkId;
    private String nodeType;
    private String username;
    private Boolean sendEmailWelcome = true;
    private long rootId;

    public PostCreateUserMsg() {

    }

    public PostCreateUserMsg(Long userId, Long storeId, Long parentIntroId, Long parentNetworkId, String nodeType, String username, Boolean sendEmailWelcome, Long rootId) {
        this.userId = userId;
        this.storeId = storeId;
        this.parentIntroId = parentIntroId;
        this.parentNetworkId = parentNetworkId;
        this.nodeType = nodeType;
        this.username = username;
        this.sendEmailWelcome = sendEmailWelcome;
        this.rootId = rootId;
    }

    public PostCreateUserMsg(Long userId, Long parentIntroId, Long parentNetworkId, String nodeType, String username, Boolean sendEmailWelcome, Long rootId) {
        this.userId = userId;
        this.parentIntroId = parentIntroId;
        this.parentNetworkId = parentNetworkId;
        this.nodeType = nodeType;
        this.username = username;
        this.sendEmailWelcome = sendEmailWelcome;
        this.rootId = rootId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getParentIntroId() {
        return parentIntroId;
    }

    public void setParentIntroId(Long parentIntroId) {
        this.parentIntroId = parentIntroId;
    }

    public Long getParentNetworkId() {
        return parentNetworkId;
    }

    public void setParentNetworkId(Long parentNetworkId) {
        this.parentNetworkId = parentNetworkId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getSendEmailWelcome() {
        return sendEmailWelcome;
    }

    public void setSendEmailWelcome(Boolean sendEmailWelcome) {
        this.sendEmailWelcome = sendEmailWelcome;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    @Override
    public String toString() {
        return "PostCreateUserMessage{" +
                "userId=" + userId +
                ", storeId=" + storeId +
                ", parentIntroId=" + parentIntroId +
                ", parentNetworkId=" + parentNetworkId +
                ", nodeType='" + nodeType + '\'' +
                ", username='" + username + '\'' +
                ", sendEmailWelcome=" + sendEmailWelcome +
                ", rootId=" + rootId +
                '}';
    }
}
