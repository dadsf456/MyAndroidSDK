package com.example.myandroidsdk.ui.bean;

/**
 * 作者：Created by dadsf456 on 2021-01-13 22:19
 * 邮箱：
 * 描述：测试 用传统写法来
 */
public class MyUserToken {

    /**
     * code : 0
     * msg : success
     * data : {"access_token":"3R5h6AEVQQmER016kzsZQ3m-5bf_M0k2","nickname":null,
     * "avatar_url":"fBnwad9Ve0cGMr4UOiO4uox3Vwy4494N","is_distributor":0,"id":307}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * access_token : 3R5h6AEVQQmER016kzsZQ3m-5bf_M0k2
         * nickname : null
         * avatar_url : fBnwad9Ve0cGMr4UOiO4uox3Vwy4494N
         * is_distributor : 0
         * id : 307
         */

        private String access_token;
        private Object nickname;
        private String avatar_url;
        private int is_distributor;
        private int id;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public int getIs_distributor() {
            return is_distributor;
        }

        public void setIs_distributor(int is_distributor) {
            this.is_distributor = is_distributor;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
