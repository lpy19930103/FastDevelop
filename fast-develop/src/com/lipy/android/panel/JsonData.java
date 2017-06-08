package com.lipy.android.panel;

import com.lipy.android.http.DataObject;

import java.util.List;

/**
 * Created by lipy on 2017/6/8.
 */

public class JsonData implements DataObject{


    /**
     * ec : 0
     * em :
     * title : ["时间","城市","施工面积","竣工面积","销售面积","现房","期房","待销面积"]
     * results : [{"city":"北京","date":"20170101","construct":"1000","becompleted":"2000","market":"1000","readyhouse":"1000","propertyhouse":"1000","forsale":"1000"},{"city":"北京","date":"20170101","construct":"1000","becompleted":"2000","market":"1000","readyhouse":"1000","propertyhouse":"1000","forsale":"1000"},{"city":"北京","date":"20170101","construct":"1000","becompleted":"2000","market":"1000","readyhouse":"1000","propertyhouse":"1000","forsale":"1000"},{"city":"北京","date":"20170101","construct":"1000","becompleted":"2000","market":"1000","readyhouse":"1000","propertyhouse":"1000","forsale":"1000"},{"city":"北京","date":"20170101","construct":"1000","becompleted":"2000","market":"1000","readyhouse":"1000","propertyhouse":"1000","forsale":"1000"}]
     */

    private String ec;
    private String em;
    private List<ResultsBean> results;

    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public String getEm() {
        return em;
    }

    public void setEm(String em) {
        this.em = em;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * city : 北京
         * date : 20170101
         * construct : 1000
         * becompleted : 2000
         * market : 1000
         * readyhouse : 1000
         * propertyhouse : 1000
         * forsale : 1000
         */

        private String city;
        private String date;
        private String construct;
        private String becompleted;
        private String market;
        private String readyhouse;
        private String propertyhouse;
        private String forsale;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getConstruct() {
            return construct;
        }

        public void setConstruct(String construct) {
            this.construct = construct;
        }

        public String getBecompleted() {
            return becompleted;
        }

        public void setBecompleted(String becompleted) {
            this.becompleted = becompleted;
        }

        public String getMarket() {
            return market;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public String getReadyhouse() {
            return readyhouse;
        }

        public void setReadyhouse(String readyhouse) {
            this.readyhouse = readyhouse;
        }

        public String getPropertyhouse() {
            return propertyhouse;
        }

        public void setPropertyhouse(String propertyhouse) {
            this.propertyhouse = propertyhouse;
        }

        public String getForsale() {
            return forsale;
        }

        public void setForsale(String forsale) {
            this.forsale = forsale;
        }
    }
}
