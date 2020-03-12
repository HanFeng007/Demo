package com.example.mvpdemo.bean;

import java.util.List;

/**
 * @ClassName: HistoryBean
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/6 10:38
 */
public class HistoryBean {
    /**
     * result : [{"_id":"19241001","title":"美国总统吉米·卡特出生","pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201110/2/467826820.jpg","year":1924,"month":10,"day":1,"des":"在96年前的今天，1924年10月1日 (农历九月初三)，美国总统吉米·卡特出生。","content":"在96年前的今天，1924年10月1日 (农历九月初三)，美国总统吉米·卡特出生。\n","lunar":"甲子年九月初三"}]
     * reason : 请求成功！
     * error_code : 0
     */

    private String reason;
    private int error_code;
    private List<ResultBean> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * _id : 19241001
         * title : 美国总统吉米·卡特出生
         * pic : http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201110/2/467826820.jpg
         * year : 1924
         * month : 10
         * day : 1
         * des : 在96年前的今天，1924年10月1日 (农历九月初三)，美国总统吉米·卡特出生。
         * content : 在96年前的今天，1924年10月1日 (农历九月初三)，美国总统吉米·卡特出生。
         * lunar : 甲子年九月初三
         */

        private String _id;
        private String title;
        private String pic;
        private int year;
        private int month;
        private int day;
        private String des;
        private String content;
        private String lunar;
        private boolean isDetail;//自定义类型

        public boolean isDetail() {
            return isDetail;
        }

        public void setDetail(boolean detail) {
            isDetail = detail;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLunar() {
            return lunar;
        }

        public void setLunar(String lunar) {
            this.lunar = lunar;
        }
    }
}
