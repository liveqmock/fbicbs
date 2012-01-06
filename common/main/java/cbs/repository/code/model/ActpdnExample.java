package cbs.repository.code.model;

import java.util.ArrayList;
import java.util.List;

public class ActpdnExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public ActpdnExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andOrgidtIsNull() {
            addCriterion("ORGIDT is null");
            return (Criteria) this;
        }

        public Criteria andOrgidtIsNotNull() {
            addCriterion("ORGIDT is not null");
            return (Criteria) this;
        }

        public Criteria andOrgidtEqualTo(String value) {
            addCriterion("ORGIDT =", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtNotEqualTo(String value) {
            addCriterion("ORGIDT <>", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtGreaterThan(String value) {
            addCriterion("ORGIDT >", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtGreaterThanOrEqualTo(String value) {
            addCriterion("ORGIDT >=", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtLessThan(String value) {
            addCriterion("ORGIDT <", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtLessThanOrEqualTo(String value) {
            addCriterion("ORGIDT <=", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtLike(String value) {
            addCriterion("ORGIDT like", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtNotLike(String value) {
            addCriterion("ORGIDT not like", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtIn(List<String> values) {
            addCriterion("ORGIDT in", values, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtNotIn(List<String> values) {
            addCriterion("ORGIDT not in", values, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtBetween(String value1, String value2) {
            addCriterion("ORGIDT between", value1, value2, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtNotBetween(String value1, String value2) {
            addCriterion("ORGIDT not between", value1, value2, "orgidt");
            return (Criteria) this;
        }

        public Criteria andPrdcdeIsNull() {
            addCriterion("PRDCDE is null");
            return (Criteria) this;
        }

        public Criteria andPrdcdeIsNotNull() {
            addCriterion("PRDCDE is not null");
            return (Criteria) this;
        }

        public Criteria andPrdcdeEqualTo(String value) {
            addCriterion("PRDCDE =", value, "prdcde");
            return (Criteria) this;
        }

        public Criteria andPrdcdeNotEqualTo(String value) {
            addCriterion("PRDCDE <>", value, "prdcde");
            return (Criteria) this;
        }

        public Criteria andPrdcdeGreaterThan(String value) {
            addCriterion("PRDCDE >", value, "prdcde");
            return (Criteria) this;
        }

        public Criteria andPrdcdeGreaterThanOrEqualTo(String value) {
            addCriterion("PRDCDE >=", value, "prdcde");
            return (Criteria) this;
        }

        public Criteria andPrdcdeLessThan(String value) {
            addCriterion("PRDCDE <", value, "prdcde");
            return (Criteria) this;
        }

        public Criteria andPrdcdeLessThanOrEqualTo(String value) {
            addCriterion("PRDCDE <=", value, "prdcde");
            return (Criteria) this;
        }

        public Criteria andPrdcdeLike(String value) {
            addCriterion("PRDCDE like", value, "prdcde");
            return (Criteria) this;
        }

        public Criteria andPrdcdeNotLike(String value) {
            addCriterion("PRDCDE not like", value, "prdcde");
            return (Criteria) this;
        }

        public Criteria andPrdcdeIn(List<String> values) {
            addCriterion("PRDCDE in", values, "prdcde");
            return (Criteria) this;
        }

        public Criteria andPrdcdeNotIn(List<String> values) {
            addCriterion("PRDCDE not in", values, "prdcde");
            return (Criteria) this;
        }

        public Criteria andPrdcdeBetween(String value1, String value2) {
            addCriterion("PRDCDE between", value1, value2, "prdcde");
            return (Criteria) this;
        }

        public Criteria andPrdcdeNotBetween(String value1, String value2) {
            addCriterion("PRDCDE not between", value1, value2, "prdcde");
            return (Criteria) this;
        }

        public Criteria andPdnseqIsNull() {
            addCriterion("PDNSEQ is null");
            return (Criteria) this;
        }

        public Criteria andPdnseqIsNotNull() {
            addCriterion("PDNSEQ is not null");
            return (Criteria) this;
        }

        public Criteria andPdnseqEqualTo(Integer value) {
            addCriterion("PDNSEQ =", value, "pdnseq");
            return (Criteria) this;
        }

        public Criteria andPdnseqNotEqualTo(Integer value) {
            addCriterion("PDNSEQ <>", value, "pdnseq");
            return (Criteria) this;
        }

        public Criteria andPdnseqGreaterThan(Integer value) {
            addCriterion("PDNSEQ >", value, "pdnseq");
            return (Criteria) this;
        }

        public Criteria andPdnseqGreaterThanOrEqualTo(Integer value) {
            addCriterion("PDNSEQ >=", value, "pdnseq");
            return (Criteria) this;
        }

        public Criteria andPdnseqLessThan(Integer value) {
            addCriterion("PDNSEQ <", value, "pdnseq");
            return (Criteria) this;
        }

        public Criteria andPdnseqLessThanOrEqualTo(Integer value) {
            addCriterion("PDNSEQ <=", value, "pdnseq");
            return (Criteria) this;
        }

        public Criteria andPdnseqIn(List<Integer> values) {
            addCriterion("PDNSEQ in", values, "pdnseq");
            return (Criteria) this;
        }

        public Criteria andPdnseqNotIn(List<Integer> values) {
            addCriterion("PDNSEQ not in", values, "pdnseq");
            return (Criteria) this;
        }

        public Criteria andPdnseqBetween(Integer value1, Integer value2) {
            addCriterion("PDNSEQ between", value1, value2, "pdnseq");
            return (Criteria) this;
        }

        public Criteria andPdnseqNotBetween(Integer value1, Integer value2) {
            addCriterion("PDNSEQ not between", value1, value2, "pdnseq");
            return (Criteria) this;
        }

        public Criteria andPdnyerIsNull() {
            addCriterion("PDNYER is null");
            return (Criteria) this;
        }

        public Criteria andPdnyerIsNotNull() {
            addCriterion("PDNYER is not null");
            return (Criteria) this;
        }

        public Criteria andPdnyerEqualTo(String value) {
            addCriterion("PDNYER =", value, "pdnyer");
            return (Criteria) this;
        }

        public Criteria andPdnyerNotEqualTo(String value) {
            addCriterion("PDNYER <>", value, "pdnyer");
            return (Criteria) this;
        }

        public Criteria andPdnyerGreaterThan(String value) {
            addCriterion("PDNYER >", value, "pdnyer");
            return (Criteria) this;
        }

        public Criteria andPdnyerGreaterThanOrEqualTo(String value) {
            addCriterion("PDNYER >=", value, "pdnyer");
            return (Criteria) this;
        }

        public Criteria andPdnyerLessThan(String value) {
            addCriterion("PDNYER <", value, "pdnyer");
            return (Criteria) this;
        }

        public Criteria andPdnyerLessThanOrEqualTo(String value) {
            addCriterion("PDNYER <=", value, "pdnyer");
            return (Criteria) this;
        }

        public Criteria andPdnyerLike(String value) {
            addCriterion("PDNYER like", value, "pdnyer");
            return (Criteria) this;
        }

        public Criteria andPdnyerNotLike(String value) {
            addCriterion("PDNYER not like", value, "pdnyer");
            return (Criteria) this;
        }

        public Criteria andPdnyerIn(List<String> values) {
            addCriterion("PDNYER in", values, "pdnyer");
            return (Criteria) this;
        }

        public Criteria andPdnyerNotIn(List<String> values) {
            addCriterion("PDNYER not in", values, "pdnyer");
            return (Criteria) this;
        }

        public Criteria andPdnyerBetween(String value1, String value2) {
            addCriterion("PDNYER between", value1, value2, "pdnyer");
            return (Criteria) this;
        }

        public Criteria andPdnyerNotBetween(String value1, String value2) {
            addCriterion("PDNYER not between", value1, value2, "pdnyer");
            return (Criteria) this;
        }

        public Criteria andRecstsIsNull() {
            addCriterion("RECSTS is null");
            return (Criteria) this;
        }

        public Criteria andRecstsIsNotNull() {
            addCriterion("RECSTS is not null");
            return (Criteria) this;
        }

        public Criteria andRecstsEqualTo(String value) {
            addCriterion("RECSTS =", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsNotEqualTo(String value) {
            addCriterion("RECSTS <>", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsGreaterThan(String value) {
            addCriterion("RECSTS >", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsGreaterThanOrEqualTo(String value) {
            addCriterion("RECSTS >=", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsLessThan(String value) {
            addCriterion("RECSTS <", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsLessThanOrEqualTo(String value) {
            addCriterion("RECSTS <=", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsLike(String value) {
            addCriterion("RECSTS like", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsNotLike(String value) {
            addCriterion("RECSTS not like", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsIn(List<String> values) {
            addCriterion("RECSTS in", values, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsNotIn(List<String> values) {
            addCriterion("RECSTS not in", values, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsBetween(String value1, String value2) {
            addCriterion("RECSTS between", value1, value2, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsNotBetween(String value1, String value2) {
            addCriterion("RECSTS not between", value1, value2, "recsts");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTPDN
     *
     * @mbggenerated do_not_delete_during_merge Sun Nov 21 21:36:06 CST 2010
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTPDN
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}